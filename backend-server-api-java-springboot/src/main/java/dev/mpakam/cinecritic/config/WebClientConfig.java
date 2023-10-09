package dev.mpakam.cinecritic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${omdb.api.baseUrl}")
    private String omdbBaseUrl;

    /**
     * This method creates and returns an instance of the WebClient class for the OMDB API.
     *
     * @return         	an instance of the WebClient class
     */
    @Bean
    public WebClient omdbWebClient(){
        return WebClient.builder()
                .baseUrl(omdbBaseUrl)
                .build();
    }
}
