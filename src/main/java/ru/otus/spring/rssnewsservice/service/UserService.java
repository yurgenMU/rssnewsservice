package ru.otus.spring.rssnewsservice.service;

import ru.otus.spring.rssnewsservice.domain.user.User;

public interface UserService {

    User saveUser(String login, String password);

    User findByLogin(String login);

    User findByCredentials(String login, String password);
}
