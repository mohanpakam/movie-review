package dev.mpakam.cinecritic.controller;

import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;


    @GetMapping("/{movieId}")
    public Mono<Movie> fetchMovie(@PathVariable String movieId){
        return movieService.findByMovieId(movieId);
    }

    @GetMapping("/omdb/{name}")
    public Flux<MovieDto> retrieveAllMoviesWithName(@PathVariable String name){
        return movieService.retrieveMoviesWithName(name);
    }
}
