package ru.otus.spring.rssnewsservice.service;

import ru.otus.spring.rssnewsservice.domain.feed.FeedData;
import ru.otus.spring.rssnewsservice.domain.feed.FeedDto;

import java.util.List;

public interface NewsService {

    FeedData getFeedById(Long id);

    void addFeedData(String name, String link);

    void updateFeedData(Long id, String name, String link);

    void removeFeedData(Long id);

    List<FeedDto> getAll();

    List<FeedDto> getNewsForUser(String username);
}
