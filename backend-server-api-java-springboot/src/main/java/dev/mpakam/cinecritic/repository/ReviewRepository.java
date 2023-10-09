package dev.mpakam.cinecritic.repository;

import dev.mpakam.cinecritic.entity.Review;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<Review, String> {

    Flux<Review> findByMovieId(ObjectId movieId);

    Mono<Review> findByUserIdAndMovieId(ObjectId userId, ObjectId movieId);


}
