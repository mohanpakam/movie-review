package dev.mpakam.cinecritic.service;

import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.dto.OmdbSearchResult;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Validated
public class OmdbAPIService {
    private String omdbApiKey;

    private WebClient omdbWebClient;

    public OmdbAPIService(@Value("${omdb.api.key}")String omdbApiKey, WebClient omdbWebClient) {
        this.omdbWebClient = omdbWebClient;
        this.omdbApiKey = omdbApiKey;
    }


    public Flux<MovieDto> retrieveMoviesWithName(@NotBlank String name) {
        return omdbWebClient.get().uri("/?s={name}&apikey={key}", name, omdbApiKey)
                .retrieve()
                .bodyToMono(OmdbSearchResult.class)
                .map(OmdbSearchResult::getSearch)
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<MovieDto> getMovieByImdbId(@NotNull String  imdbId) {
        return omdbWebClient.get().uri("/?i={imdbId}&apikey={key}", imdbId, omdbApiKey)
                .retrieve()
                .bodyToMono(MovieDto.class);
    }
}
