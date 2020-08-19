package ru.otus.spring.rssnewsservice.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.rssnewsservice.domain.feed.FeedData;
import ru.otus.spring.rssnewsservice.domain.feed.FeedDto;
import ru.otus.spring.rssnewsservice.domain.user.User;
import ru.otus.spring.rssnewsservice.exception.FeedDataNotFoundException;
import ru.otus.spring.rssnewsservice.repository.FeedDataRepository;
import ru.otus.spring.rssnewsservice.repository.UserRepository;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final FeedDataRepository feedDataRepository;
    private final RSSParser rssParser;
    private final UserRepository userRepository;

    public NewsServiceImpl(FeedDataRepository feedDataRepository, RSSParser rssParser, UserRepository userRepository) {
        this.feedDataRepository = feedDataRepository;
        this.rssParser = rssParser;
        this.userRepository = userRepository;
    }


    @Override
    public FeedData getFeedById(Long id) {
        return feedDataRepository.findById(id)
                .orElseThrow(() -> new FeedDataNotFoundException("Feed data with this identifier not found"));
    }

    @Override
    public FeedData addFeedData(String name, String link) {
        return feedDataRepository.save(new FeedData(name, link));
    }

    @Override
    public void updateFeedData(Long id, String name, String link) {
        feedDataRepository.findById(id)
                .ifPresent(feedDataFound -> feedDataRepository.save(new FeedData(name, link).setId(feedDataFound.getId())));
    }

    @Override
    public void removeFeedData(Long id) {
        feedDataRepository.deleteById(id);
    }

    @Override
    public List<FeedDto> getAll() {
        List<FeedData> allFeedData = (List<FeedData>) feedDataRepository.findAll();
        return allFeedData.stream()
                .map(getFeedDataFeedDtoFunction())
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedDto> getNewsForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return user.getUserFeeds().stream()
                .map(getFeedDataFeedDtoFunction())
                .collect(Collectors.toList());
    }

    @Override
    public List<FeedData> getAllTechnicalFeeds() {
        return (List<FeedData>) feedDataRepository.findAll();
    }

    private Function<FeedData, FeedDto> getFeedDataFeedDtoFunction() {
        return feedData -> rssParser
                .getFeedFromLink(feedData.getLink()).setId(feedData.getId());
    }
}
