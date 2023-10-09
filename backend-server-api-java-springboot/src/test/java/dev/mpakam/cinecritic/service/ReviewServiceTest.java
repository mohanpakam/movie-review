package dev.mpakam.cinecritic.service;

import dev.mpakam.cinecritic.dto.ReviewDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.entity.Review;
import dev.mpakam.cinecritic.entity.User;
import dev.mpakam.cinecritic.repository.ReviewRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private MovieService movieService;

    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewService = new ReviewService(reviewRepository, userService, movieService);
    }

    @Test
    void testCreateReview() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUserId("6522370e3e317a55629b503c");
        reviewDto.setMovieId("movie123");
        reviewDto.setComment("This is a review");
        reviewDto.setRating(5);

        ObjectId id = new ObjectId("6522370e3e317a55629b503c");

        User user = new User();
        user.setName("John");

        Movie movie = Movie.builder()
                .imdbId("tt1234567")
                .title("Movie Title")
                .movieId(id)
                .build();

        Review review = Review.builder().reviewId(id).movieId(id).build();

        when(userService.getUser(any())).thenReturn(Mono.just(user));
        when(movieService.findMovieOrCreate(any(ReviewDto.class))).thenReturn(Mono.just(movie));
        when(movieService.findByMovieId(any())).thenReturn(Mono.just(movie));
        when(movieService.findByImdbId(any())).thenReturn(Mono.just(movie));
        when(reviewRepository.findByUserIdAndMovieId(any(ObjectId.class), any(ObjectId.class))).thenReturn(Mono.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(Mono.just(review));

        StepVerifier.create(reviewService.createReview(reviewDto))
                .expectNextMatches(savedReviewDto ->
                        savedReviewDto.getUserId().equals(reviewDto.getUserId()) &&
                                savedReviewDto.getMovieId().equals(reviewDto.getMovieId()) &&
                                savedReviewDto.getComment().equals(reviewDto.getComment()) &&
                                savedReviewDto.getRating() == reviewDto.getRating() &&
                                savedReviewDto.getName().equals(user.getName())
                )
                .verifyComplete();
    }
    @Test
    void testFetchAllReviewsByMovieId() {
        String omdbId = "tt1234567";
        Movie movie = new Movie();
        ObjectId id = new ObjectId("6522370e3e317a55629b503c");
        movie.setMovieId(id);

        Review review = Review.builder().reviewId(id).movieId(id).build();

        when(movieService.findByImdbId(omdbId)).thenReturn(Mono.just(movie));
        when(reviewRepository.findByMovieId(any(ObjectId.class))).thenReturn(Flux.just(review));

        StepVerifier.create(reviewService.fetchAllReviewsByMovieId(omdbId))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testFindMyReviewByUserIdMovieId() {
        String userId = "6522370e3e317a55629b503c";
        String omdbId = "tt1234567";

        ObjectId id = new ObjectId("6522370e3e317a55629b503c");

        User user = new User();
        user.setName("John");

        Movie movie = Movie.builder().imdbId(omdbId).movieId(id).build();

        Review review = Review.builder().reviewId(id).movieId(id).build();

        when(userService.getUser(userId)).thenReturn(Mono.just(user));
        when(movieService.findByImdbId(omdbId)).thenReturn(Mono.just(movie));
        when(reviewRepository.findByUserIdAndMovieId(any(ObjectId.class), any(ObjectId.class)))
                .thenReturn(Mono.just(review));

        StepVerifier.create(reviewService.findMyReviewByUserIdMovieId(userId, omdbId))
                .expectNextCount(1)
                .verifyComplete();
    }

}
