package dev.mpakam.cinecritic.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@SuperBuilder
public class UserMovieDto extends UserDto{
    private List<MovieDto> movieWatchList;
    private List<MovieDto> movieReviewedList;
}
