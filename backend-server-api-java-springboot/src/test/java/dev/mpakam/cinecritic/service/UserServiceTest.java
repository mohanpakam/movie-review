package dev.mpakam.cinecritic.service;

import dev.mpakam.cinecritic.dto.UserDto;
import dev.mpakam.cinecritic.entity.Movie;
import dev.mpakam.cinecritic.entity.User;
import dev.mpakam.cinecritic.repository.MovieRepository;
import dev.mpakam.cinecritic.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {
        UserDto userDto = UserDto.builder()
                .name("John")
                .emailId("john@example.com")
                .build();

        User user = User.builder()
                .name(userDto.getName())
                .emailId(userDto.getEmailId())
                .userId(new ObjectId("6522370e3e317a55629b503c"))
                .build();

        when(userRepository.findByEmailId(userDto.getEmailId())).thenReturn(Mono.empty());
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(userService.saveUser(userDto))
                .expectNextMatches(savedUserDto ->
                        savedUserDto.getName().equals(userDto.getName()) &&
                                savedUserDto.getEmailId().equals(userDto.getEmailId()))
                .verifyComplete();
    }

    @Test
    void testGetUser() {
        String userId = "6522370e3e317a55629b503c";
        ObjectId objectId = new ObjectId(userId);

        when(userRepository.findById(objectId)).thenReturn(Mono.just(new User()));

        StepVerifier.create(userService.getUser(userId))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testAddToWatchList() {
        String userId = "6522370e3e317a55629b503c";
        String imdbId = "tt1234567";

        User user = new User();
        user.setWatchList(new ArrayList<>());

        Movie movie = new Movie();
        movie.setImdbId(imdbId);

        when(userRepository.findById(any(ObjectId.class))).thenReturn(Mono.just(user));
        when(movieService.findMovieOrCreate(imdbId)).thenReturn(Mono.just(movie));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            assert(savedUser.getWatchList().contains(imdbId));
            return Mono.just(savedUser);
        });

        userService.addToWatchList(userId, imdbId);
    }

    @Test
    void testRemoveFromWatchList() {
        String userId = "6522370e3e317a55629b503c";
        String imdbId = "tt1234567";

        User user = new User();
        user.setWatchList(new ArrayList<>(Collections.singletonList(imdbId)));

        when(userRepository.findById(any(ObjectId.class))).thenReturn(Mono.just(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            assert(!savedUser.getWatchList().contains(imdbId));
            return Mono.just(savedUser);
        });

        userService.removeFromWatchList(userId, imdbId);
    }

    @Test
    void testAddToReviewed() {
        User user = new User();
        user.setReviewedList(new ArrayList<>());

        userService.addToReviewed(user, "tt1234567");

        assert(user.getReviewedList().contains("tt1234567"));
    }

    @Test
    void testGetUserMovies() {
        String userId = "6522370e3e317a55629b503c";
        User user = new User();
        user.setWatchList(Collections.singletonList("tt1234567"));

        when(userRepository.findById(any(ObjectId.class))).thenReturn(Mono.just(user));
        when(movieRepository.findByImdbIdIn(any(List.class))).thenReturn(Flux.just(new Movie()));

        StepVerifier.create(userService.getUserMovies(userId))
                .expectNextCount(1)
                .verifyComplete();
    }

}
