package ru.otus.spring.rssnewsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.rssnewsservice.domain.feed.FeedData;

public interface FeedDataRepository extends JpaRepository<FeedData, Long> {

}
