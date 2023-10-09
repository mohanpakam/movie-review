package dev.mpakam.cinecritic.service;

import dev.mpakam.cinecritic.dto.ReviewDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.entity.Review;
import dev.mpakam.cinecritic.entity.User;
import dev.mpakam.cinecritic.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static dev.mpakam.cinecritic.exception.ExceptionEnum.REVIEW_ALREADY_EXISTS;
import static dev.mpakam.cinecritic.exception.ExceptionEnum.USER_DOES_NOT_EXIST;

@Slf4j
@Service
@AllArgsConstructor
public class ReviewService {
    private ReviewRepository reviewRepository;
    private UserService userService;
    private MovieService movieService;

    /**
     * Creates a new review based on the provided ReviewDto.
     * This also creates Movie object if it does not exist
     *
     * @param  reviewDto   the ReviewDto object containing the details of the review
     * @return             the Mono object representing the created review
     */
    public Mono<ReviewDto> createReview(ReviewDto reviewDto){
        return userService.getUser(reviewDto.getUserId())
                .switchIfEmpty(Mono.error(USER_DOES_NOT_EXIST.createCustomException(reviewDto.getUserId())))
                .flatMap(user -> movieService.findMovieOrCreate(reviewDto)
                        .flatMap(movie -> findMyReviewByUserIdMovieId(reviewDto.getUserId(), movie.getImdbId())
                                .flatMap(existingReview -> {
                                    // If an existing review is found, throw an error
                                    return Mono.error(REVIEW_ALREADY_EXISTS.createCustomException(existingReview.getReviewId()));
                                })
                                .switchIfEmpty(saveReview(movie, reviewDto, user)
                                        .map(review -> updateReviewDto(reviewDto, review, movie, user))
                                )
                        )
                ).cast(ReviewDto.class);
    }

    private ReviewDto updateReviewDto(ReviewDto reviewDto, Review review, Movie movie, User user){
        reviewDto.setReviewId(review.getReviewId().toHexString());
        reviewDto.setName(user.getName());
        reviewDto.setMovieId(movie.getMovieId().toString());
        reviewDto.setCreatedAt(review.getCreatedAt());
        return reviewDto;
    }

    public Mono<Review> saveReview(Movie movie, ReviewDto reviewDto, User user){
        Mono<Review> reviewMono = reviewRepository.save(Review.builder()
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
                .userDisplayName(user.getName())
                .userId(new ObjectId(reviewDto.getUserId()))
                .movieId(movie.getMovieId()) // Set movieId here
                .createdAt(LocalDateTime.now())
                .build());
        log.debug("After adding the review {}", reviewMono);
        userService.addToReviewed(user, movie.getImdbId());
        return reviewMono;
    }


    public Flux<ReviewDto> fetchAllReviewsByMovieId(String omdbId) {
        return movieService.findByImdbId(omdbId)
                .map(movie ->
                    reviewRepository.findByMovieId(new ObjectId(movie.getMovieId().toString())).map(this::mapReviewToDto)
                        ). flatMapMany(Flux::from);
    }

    public Mono<ReviewDto> findMyReviewByUserIdMovieId(String userId, String omdbId){
        return userService.getUser(userId)
                .switchIfEmpty(Mono.error(USER_DOES_NOT_EXIST.createCustomException(userId)))
                .flatMap(user -> movieService.findByImdbId(omdbId)
                .map(movie -> reviewRepository.findByUserIdAndMovieId(new ObjectId(userId), movie.getMovieId())
                        .map(this::mapReviewToDto)))
                .flatMap(innerMono -> innerMono);
    }
    private Review mapReviewToEntity(Movie movie, ReviewDto reviewDto){
        return Review.builder()
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
                .userDisplayName(reviewDto.getName())
                .userId(new ObjectId(reviewDto.getUserId()))
                .movieId(new ObjectId(reviewDto.getMovieId()))
                .createdAt(reviewDto.getCreatedAt())
                .build();
    }

    private ReviewDto mapReviewToDto(Review review){
        return ReviewDto.builder()
                .name(review.getUserDisplayName())
                .reviewId(review.getReviewId().toHexString())
                .movieId(review.getMovieId().toHexString())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
