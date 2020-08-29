package ru.otus.spring.rssnewsservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rssnewsservice.domain.feed.FeedData;
import ru.otus.spring.rssnewsservice.domain.user.Authority;
import ru.otus.spring.rssnewsservice.domain.user.AuthorityType;
import ru.otus.spring.rssnewsservice.domain.user.User;
import ru.otus.spring.rssnewsservice.exception.FeedDataNotFoundException;
import ru.otus.spring.rssnewsservice.repository.FeedDataRepository;
import ru.otus.spring.rssnewsservice.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final FeedDataRepository feedDataRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, FeedDataRepository feedDataRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.feedDataRepository = feedDataRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User saveUser(String login, String password) {
        return userRepository.save(new User()
                .setUsername(login)
                .setPassword(passwordEncoder.encode(password))
                .setAuthorities(Set.of(new Authority(AuthorityType.USER))));
    }

    @Override
    @Transactional
    public User updateUser(String login, List<Long> feeds) {
        List<FeedData> feedsFound = feeds.stream()
                .map(feedDataRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get).collect(Collectors.toList());

        User userFound = userRepository.findByUsername(login)
                .orElseThrow(() -> new FeedDataNotFoundException("User with this identifier not found"));

        return userRepository.save(new User().setId(userFound.getId())
                .setUsername(userFound.getUsername())
                .setPassword(userFound.getPassword())
                .setAuthorities(userFound.getAuthorities())
                .setUserFeeds(feedsFound));
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
