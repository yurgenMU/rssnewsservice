package ru.otus.spring.rssnewsservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.rssnewsservice.domain.user.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
