package ru.otus.spring.rssnewsservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.spring.rssnewsservice.domain.FeedData;

import java.util.List;

public interface FeedDataRepository extends CrudRepository<FeedData, Long> {

}
