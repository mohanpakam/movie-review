package dev.mpakam.cinecritic.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.mpakam.cinecritic.config.PartialMaskingSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String reviewId;
    private String userId;
    private String comment;
    private String imdbId;
    private String title;
    private String releaseDate;
    private String poster;
    private String movieId;
    @JsonSerialize(using = PartialMaskingSerializer.class)
    private String name;
    private int rating;
    private LocalDateTime createdAt;
}
