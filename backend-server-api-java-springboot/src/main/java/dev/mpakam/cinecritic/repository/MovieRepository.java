package dev.mpakam.cinecritic.repository;

import dev.mpakam.cinecritic.entity.Movie;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface MovieRepository extends ReactiveMongoRepository<Movie, ObjectId> {
    Mono<Movie> findByImdbId(String imdbId);

    Flux<Movie> findByImdbIdIn(List<String> imdbIds);
}
