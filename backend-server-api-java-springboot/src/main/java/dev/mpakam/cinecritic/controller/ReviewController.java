package dev.mpakam.cinecritic.controller;

import dev.mpakam.cinecritic.dto.ReviewDto;
import dev.mpakam.cinecritic.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private ReviewService reviewService;

    /**
     * Adds a new review.
     *
     * @param  reviewDto  the review data transfer object
     * @return            the review data transfer object
     */
    @PostMapping
    public Mono<ReviewDto> addNewReview(@RequestBody ReviewDto reviewDto){
        return reviewService.createReview(reviewDto);
    }


    /**
     * Returns a Flux of ReviewDto objects for a given movie ID.
     *
     * @param imdbId the ID of the movie
     * @return a Flux of ReviewDto objects
     */
    @GetMapping("/all/movie/{imdbId}")
    public Flux<ReviewDto> listReviews(@PathVariable String imdbId){
        return reviewService.fetchAllReviewsByMovieId(imdbId);
    }

    @GetMapping("/user/{userId}/movie/{omdbId}")
    public Mono<ReviewDto> getReview(@PathVariable("userId") String userId, @PathVariable("omdbId") String omdbId){
        return reviewService.findMyReviewByUserIdMovieId(userId, omdbId);
    }
}
