package dev.mpakam.cinecritic.service;


import dev.mpakam.cinecritic.dto.MovieDto;
import dev.mpakam.cinecritic.dto.UserDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.entity.User;
import dev.mpakam.cinecritic.repository.MovieRepository;
import dev.mpakam.cinecritic.repository.UserRepository;
import dev.mpakam.cinecritic.exception.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;


@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private MovieService movieService;
    private MovieRepository movieRepository;

    public Mono<UserDto> saveUser(UserDto userDto){
        return userRepository.findByEmailId(userDto.getEmailId())
                .switchIfEmpty(Mono.defer(()->createUser(userDto)))
                .map(user -> {
                    userDto.setUserId(user.getUserId().toHexString());
                    userDto.setName(user.getName());
                    userDto.setWatchList(user.getWatchList());
                    userDto.setReviewedList(user.getReviewedList());
                    return userDto;
                }).flatMap(user-> getUserWatchListMovies(user.getUserId())
                        .collectList().map(movieList -> {
                            userDto.setMovieWatchList(movieList);
                            return userDto;
                        }));
    }
    private Mono<User> createUser(UserDto userDto){
        return userRepository.save(User.builder()
                .name(userDto.getName())
                .emailId(userDto.getEmailId())
                .build());
    }

    public Mono<User> getUser(String userId){
        return userRepository.findById(new ObjectId(userId));
    }


    public void addToWatchList(String userId, String imdbId){
        Mono<Movie> movie = movieService.findMovieOrCreate(imdbId);
        movie.doOnSuccess(movieObj -> {
            System.out.println("movieObj = " + movieObj);
        }).subscribe();
        userRepository.findById(new ObjectId(userId))
                .switchIfEmpty(Mono.error(ExceptionEnum.USER_DOES_NOT_EXIST.createCustomException(userId)))
                .flatMap(userObj -> {
                    if (userObj.getWatchList() != null && !userObj.getWatchList().contains(imdbId)) {
                        userObj.getWatchList().add(imdbId);
                    } else {
                        userObj.setWatchList(new ArrayList<>());
                        userObj.getWatchList().add(imdbId);
                    }
                    log.debug("userObj.getWatchList() = " + userObj.getWatchList());
                    return userRepository.save(userObj);
                })
                .doOnSuccess(userObj -> {
                    System.out.println("userObject = " + userObj);
                })
                .subscribe();
    }

    public void removeFromWatchList(String userId, String imdbId) {
            userRepository.findById(new ObjectId(userId))
                    .switchIfEmpty(Mono.error(ExceptionEnum.USER_DOES_NOT_EXIST.createCustomException(userId)))
                    .flatMap(userObj->{
                        if(userObj.getWatchList() != null && userObj.getWatchList().contains(imdbId)){
                            log.debug("Removing the imdbId from watchList = " + imdbId);
                            userObj.getWatchList().remove(imdbId);
                            return userRepository.save(userObj);
                        }
                        return Mono.just(userObj);
                    })
                    .doOnSuccess(userObj -> {
                            System.out.println("userObject = " + userObj);
                        }).subscribe();
    }

    public void addToReviewed(User user, String imdbId){
        user.getReviewedList().add(imdbId);
        userRepository.save(user);
    }

    //TODO: Remove this method after adding the review logic

    public Flux<MovieDto> getUserMovies(String userId) {
        return userRepository.findById(new ObjectId(userId))
                .flatMapMany(user -> movieRepository.findByImdbIdIn(user.getWatchList())
                        .map(movie -> {
                            MovieDto movieInfo = new MovieDto();
                            movieInfo.setTitle(movie.getTitle());
                            movieInfo.setImdbID(movie.getImdbId());
                            movieInfo.setYear(movie.getReleaseDate());
                            movieInfo.setPoster(movie.getPoster());
                            log.debug("movieInfo = " + movieInfo);
                            return movieInfo;
                        }));
    }

    private Flux<MovieDto> getUserWatchListMovies(String userId) {
        return userRepository.findById(new ObjectId(userId))
                .flatMapMany(user -> movieRepository.findByImdbIdIn(user.getWatchList())
                        .map(movie -> {
                            MovieDto movieInfo = new MovieDto();
                            movieInfo.setTitle(movie.getTitle());
                            movieInfo.setImdbID(movie.getImdbId());
                            movieInfo.setYear(movie.getReleaseDate());
                            movieInfo.setPoster(movie.getPoster());
                            log.debug("movieInfo = " + movieInfo);
                            return movieInfo;
                        }));
    }
}
