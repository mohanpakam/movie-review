package dev.mpakam.cinecritic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.dto.OmdbSearchResult;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.codec.DecodingException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OmdbApiServiceIntegrationTests {

    private static MockWebServer mockWebServer;

    private static WebClient omdbWebClient;

    private OmdbAPIService omdbAPIService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public  void setUp() throws IOException {
        omdbAPIService = new OmdbAPIService("key", omdbWebClient);
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        omdbWebClient = WebClient.builder()
                .baseUrl(String.format("http://localhost:%s", mockWebServer.getPort()))
                .build();
    }

    @AfterAll
    public static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetMovieByImdbId() throws JsonProcessingException {
        // Test case 1: Valid imdbId, expect MovieDto object
        String imdbId1 = "tt1234567";
        MovieDto expectedMovie1 = new MovieDto(/* initialize with test data */);
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(expectedMovie1))
                .addHeader("Content-Type", "application/json"));

        Mono<MovieDto> result1 = omdbAPIService.getMovieByImdbId(imdbId1);
        StepVerifier.create(result1)
                .expectNext(expectedMovie1)
                .verifyComplete();
    }

    @Test
    public void testGetMovieByImdbId_WhenInvalidResponse() throws JsonProcessingException {

        // Test case 3: Invalid imdbId, expect WebClientResponseException
        String imdbId3 = "invalid";

        mockWebServer.enqueue(new MockResponse().setBody("invalid").addHeader("Content-Type", "application/json"));
        Mono<MovieDto> result3 = omdbAPIService.getMovieByImdbId(imdbId3);
        StepVerifier.create(result3)
                .expectError(DecodingException.class)
                .verify();
    }

    @Test
    public void testRetrieveMoviesWithName() throws JsonProcessingException {
        // Test case 1: Testing with a valid IMDB ID
        final String imdbId = "tt1234567";
        OmdbSearchResult omdbSearchResult = new OmdbSearchResult();
        MovieDto expectedMovie1 = MovieDto.builder().imdbID("tt1234567").build();
        MovieDto expectedMovie2 = MovieDto.builder().imdbID("tt1234568").build();
        omdbSearchResult.setSearch(List.of(expectedMovie1, expectedMovie2));
        mockWebServer.enqueue(new MockResponse().setBody(objectMapper.writeValueAsString(omdbSearchResult))
                .addHeader("Content-Type", "application/json"));

        Flux<MovieDto> result = omdbAPIService.retrieveMoviesWithName(imdbId);

        StepVerifier.create(result)
                .expectNext(expectedMovie1)
                .expectNext(expectedMovie2)
                .verifyComplete();
    }

}
