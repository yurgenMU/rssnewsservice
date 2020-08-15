package ru.otus.spring.rssnewsservice.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.spring.rssnewsservice.domain.user.Authority;
import ru.otus.spring.rssnewsservice.domain.user.AuthorityType;
import ru.otus.spring.rssnewsservice.domain.user.User;
import ru.otus.spring.rssnewsservice.repository.UserRepository;

import java.util.Set;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(String login, String password) {
        return userRepository.save(new User()
                .setUsername(login)
                .setPassword(passwordEncoder.encode(password))
                .setAuthorities(Set.of(new Authority(AuthorityType.USER))));
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByUsername(login).orElse(null);
    }

    @Override
    public User findByCredentials(String login, String password) {
        User user = findByLogin(login);
        if (nonNull(user)) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
