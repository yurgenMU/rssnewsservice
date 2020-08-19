package ru.otus.spring.rssnewsservice.service;

import ru.otus.spring.rssnewsservice.domain.user.User;

import java.util.List;

public interface UserService {

    User saveUser(String login, String password);

    User updateUser(String login, List<Long> feeds);

    User findByLogin(String login);

    User findByCredentials(String login, String password);
}
