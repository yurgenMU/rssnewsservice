package ru.otus.spring.rssnewsservice.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.rssnewsservice.domain.feed.FeedData;

public interface FeedDataRepository extends CrudRepository<FeedData, Long> {

}
