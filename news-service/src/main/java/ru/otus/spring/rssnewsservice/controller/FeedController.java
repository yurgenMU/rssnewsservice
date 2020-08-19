package ru.otus.spring.rssnewsservice.controller;

import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.io.FeedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.rssnewsservice.domain.feed.FeedData;
import ru.otus.spring.rssnewsservice.domain.feed.FeedDto;
import ru.otus.spring.rssnewsservice.service.NewsService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
public class FeedController {

    private final NewsService newsService;

    public FeedController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news/all")
    public List<FeedDto> getAllFeeds()  {
        return newsService.getAll();
    }

    @GetMapping("/news/custom")
    public List<FeedDto> getFeedForUser(Principal principal) throws IOException, FeedException {
        if (isNull(principal)) {
            return newsService.getAll();
        }
        String username = principal.getName();
        return newsService.getNewsForUser(username);
    }

    @PostMapping("/news/addFeed")
    public FeedData addFeed(@RequestBody FeedData feedData) {
        return newsService.addFeedData(feedData.getName(), feedData.getLink());
    }

    @PostMapping("/news/editFeed")
    public ResponseEntity editFeed(@RequestBody FeedData feedData) {
        newsService.updateFeedData(feedData.getId(), feedData.getName(), feedData.getLink());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/news/removeFeed/{feedId}")
    public ResponseEntity deleteFeed(@PathVariable ("feedId") Long feedId) {
        newsService.removeFeedData(feedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/news/allFeeds")
    public List<FeedData> getTechnicalFeeds() throws IOException, FeedException {
        return newsService.getAllTechnicalFeeds();
    }
}
