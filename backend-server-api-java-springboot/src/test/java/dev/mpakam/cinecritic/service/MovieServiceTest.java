package dev.mpakam.cinecritic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.dto.ReviewDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.repository.MovieRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService movieService;
    @Mock
    private OmdbAPIService omdbAPIService;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieService = new MovieService(movieRepository, omdbAPIService);
    }

    @Test
    void testFindMovieOrCreate_WhenMovieExists() {
        String imdbId = "tt1234567";
        Movie existingMovie = new Movie();
        Mockito.when(movieRepository.findByImdbId(imdbId)).thenReturn(Mono.just(existingMovie));

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setImdbId(imdbId);

        StepVerifier.create(movieService.findMovieOrCreate(reviewDto))
                .expectNext(existingMovie)
                .verifyComplete();
    }

    @Test
    void testFindMovieOrCreate_WhenMovieDoesNotExist() {
        String imdbId = "tt1234567";
        Mockito.when(movieRepository.findByImdbId(imdbId)).thenReturn(Mono.empty());

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setImdbId(imdbId);

        Movie newMovie = new Movie();
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(Mono.just(newMovie));

        StepVerifier.create(movieService.findMovieOrCreate(reviewDto))
                .expectNext(newMovie)
                .verifyComplete();
    }

    @Test
    void testFindMovieOrCreate_WithImdbId_WhenMovieExists() {
        String imdbId = "tt1234567";
        Movie existingMovie = new Movie();
        Mockito.when(movieRepository.findByImdbId(imdbId)).thenReturn(Mono.just(existingMovie));

        StepVerifier.create(movieService.findMovieOrCreate(imdbId))
                .expectNext(existingMovie)
                .verifyComplete();
    }

    @Test
    void testFindMovieOrCreate_WithImdbId_WhenMovieDoesNotExist() throws JsonProcessingException {
        String imdbId = "tt1234567";
        Mockito.when(movieRepository.findByImdbId(imdbId)).thenReturn(Mono.empty());

        MovieDto movieDto = new MovieDto();
        movieDto.setImdbID(imdbId);

        Movie newMovie = new Movie();
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(Mono.just(newMovie));
        Mockito.when(omdbAPIService.getMovieByImdbId(imdbId)).thenReturn(Mono.just(new MovieDto()));


        StepVerifier.create(movieService.findMovieOrCreate(imdbId))
                .expectNext(newMovie)
                .verifyComplete();
    }

    @Test
    void testFindByMovieId() {
        String movieId = "6522370e3e317a55629b503c";
        Movie expectedMovie = new Movie();
        Mockito.when(movieRepository.findById(new ObjectId(movieId))).thenReturn(Mono.just(expectedMovie));

        StepVerifier.create(movieService.findByMovieId(movieId))
                .expectNext(expectedMovie)
                .verifyComplete();
    }

    @Test
    void testFindByImdbId() {
        String imdbId = "tt1234567";
        Movie expectedMovie = new Movie();
        Mockito.when(movieRepository.findByImdbId(imdbId)).thenReturn(Mono.just(expectedMovie));

        StepVerifier.create(movieService.findByImdbId(imdbId))
                .expectNext(expectedMovie)
                .verifyComplete();
    }

    @Test
    void testRetrieveMoviesWithName() throws JsonProcessingException {
        String name = "TestMovie";
        MovieDto movieDto1 = new MovieDto();
        MovieDto movieDto2 = new MovieDto();
        Mockito.when(omdbAPIService.retrieveMoviesWithName(name)).thenReturn(Flux.just(movieDto1, movieDto2));

        Flux<MovieDto> resultFlux = movieService.retrieveMoviesWithName(name);

        StepVerifier.create(resultFlux)
                .expectNext(movieDto1)
                .expectNext(movieDto2)
                .verifyComplete();
    }
}

