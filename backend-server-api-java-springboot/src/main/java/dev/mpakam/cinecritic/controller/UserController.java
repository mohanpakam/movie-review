package dev.mpakam.cinecritic.controller;

import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.dto.UserDto;
import dev.mpakam.cinecritic.exception.CustomException;
import dev.mpakam.cinecritic.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/new")
    public Mono<UserDto> saveNewUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

    @PatchMapping("/add-watchlist/user/{userId}/movie/{imdbId}")
    public void addToWatchlist(@PathVariable ("userId") String userId, @PathVariable("imdbId") String imdbId){
        userService.addToWatchList(userId, imdbId);
    }
    @PatchMapping("/remove-watchlist/user/{userId}/movie/{imdbId}")
    public void removeFromWatchlist(@PathVariable ("userId") String userId, @PathVariable("imdbId") String imdbId){
        userService.removeFromWatchList(userId, imdbId);
    }

    @GetMapping("/user/{userId}/watchlist")
    public Flux<MovieDto> getWatchlistReactive(@PathVariable("userId") String userId) throws CustomException {
        return userService.getUserMovies(userId);
    }

}
