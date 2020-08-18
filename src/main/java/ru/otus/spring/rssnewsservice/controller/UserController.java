package ru.otus.spring.rssnewsservice.controller;

import com.sun.syndication.io.FeedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.rssnewsservice.domain.feed.FeedDto;
import ru.otus.spring.rssnewsservice.domain.request.ErrorResponse;
import ru.otus.spring.rssnewsservice.domain.request.UserRequest;
import ru.otus.spring.rssnewsservice.domain.user.User;
import ru.otus.spring.rssnewsservice.domain.user.UserDto;
import ru.otus.spring.rssnewsservice.security.JwtProvider;
import ru.otus.spring.rssnewsservice.service.UserService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public UserController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity registerNewUser(@RequestBody UserRequest registrationRequest) {
        String login = registrationRequest.getLogin();
        String password = registrationRequest.getPassword();

        if (nonNull(userService.findByLogin(login))) {
            return processError(HttpStatus.BAD_REQUEST.value(), "User with this login already exists");
        }

        userService.saveUser(login, password);

        return getResponseEntityForSuccess(login);
    }

    private ResponseEntity getResponseEntityForSuccess(String login) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UserDto(login, jwtProvider.generateToken(login)));
    }

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@RequestBody UserRequest authenticationRequest) {
        String login = authenticationRequest.getLogin();
        String password = authenticationRequest.getPassword();

        User user = userService.findByCredentials(login, password);
        if (isNull(user)) {
            return processError(HttpStatus.UNAUTHORIZED.value(), "You've entered wrong credentials. Try again.");
        }

        return getResponseEntityForSuccess(login);
    }

    @PostMapping("/news/editFeed")
    public ResponseEntity editFeedForUser(Principal principal, @RequestBody List<FeedDto> feeds) throws IOException, FeedException {

        String name = principal.getName();
        List<Long> feedIds = feeds.stream().map(FeedDto::getId).collect(Collectors.toList());
        userService.updateUser(name, feedIds);
        return ResponseEntity.ok().build();
    }


    private ResponseEntity processError(int status, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse(status, errorMessage);
        return ResponseEntity.status(status).body(errorResponse);
    }

}
