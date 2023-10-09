package dev.mpakam.cinecritic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto {
    private String name;
    private String emailId;
    private String userId;
    private String imdbId;
    private List<String> watchList;
    private List<String> reviewedList;
    private List<MovieDto> movieWatchList;
    private List<MovieDto> movieReviewedList;
}
