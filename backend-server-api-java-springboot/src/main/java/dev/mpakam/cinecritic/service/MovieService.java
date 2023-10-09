package dev.mpakam.cinecritic.service;

import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.dto.ReviewDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MovieService {
    private MovieRepository movieRepository;
    private OmdbAPIService omdbAPIService;


    public Mono<Movie> findMovieOrCreate(ReviewDto reviewDto){
        return movieRepository.findByImdbId(reviewDto.getImdbId())
                .switchIfEmpty(Mono.defer(()->saveMovie(reviewDto)));
    }

    public Mono<Movie> findMovieOrCreate(String imdbId){
        return movieRepository.findByImdbId(imdbId)
                .switchIfEmpty(Mono.defer(()->
                        omdbAPIService.getMovieByImdbId(imdbId)
                                .flatMap(this::saveMovie)));
    }

    public Mono<Movie> findByMovieId(String movieId){
        return movieRepository.findById(new ObjectId(movieId));
    }

    public Mono<Movie> findByImdbId(String imdbId){
        return movieRepository.findByImdbId(imdbId);
    }

    private Mono<Movie> saveMovie(ReviewDto reviewDto){
        return movieRepository.save(Movie.builder()
                        .title(reviewDto.getTitle())
                        .imdbId(reviewDto.getImdbId())
                        .releaseDate(reviewDto.getReleaseDate())
                        .poster(reviewDto.getPoster())
                .build());
    }

    private Mono<Movie> saveMovie(MovieDto movieDto){
        return movieRepository.save(Movie.builder()
                .title(movieDto.getTitle())
                .imdbId(movieDto.getImdbID())
                .releaseDate(movieDto.getYear())
                .poster(movieDto.getPoster())
                .build());
    }


    public Flux<MovieDto> retrieveMoviesWithName(String name) {
        return omdbAPIService.retrieveMoviesWithName(name);
    }
}
