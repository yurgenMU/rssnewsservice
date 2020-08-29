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

    @GetMapping("/api/v1/news/all")
    public ResponseEntity<List<FeedDto>> getAllFeeds() {
        return ResponseEntity.ok(newsService.getAll());
    }

    @GetMapping("/api/v1/news/custom")
    public ResponseEntity<List<FeedDto>> getFeedForUser(Principal principal) {
        if (isNull(principal)) {
            return ResponseEntity.ok(newsService.getAll());
        }
        String username = principal.getName();
        return ResponseEntity.ok(newsService.getNewsForUser(username));
    }

    @PostMapping("/api/v1/news/addFeed")
    public ResponseEntity<FeedData> addFeed(@RequestBody FeedData feedData) {
        return ResponseEntity.ok(newsService.addFeedData(feedData.getName(), feedData.getLink()));
    }

    @PutMapping("/api/v1/news/editFeed")
    public ResponseEntity<?> editFeed(@RequestBody FeedData feedData) {
        newsService.updateFeedData(feedData.getId(), feedData.getName(), feedData.getLink());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/news/removeFeed/{feedId}")
    public ResponseEntity<?> deleteFeed(@PathVariable("feedId") Long feedId) {
        newsService.removeFeedData(feedId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v1/news/allFeeds")
    public ResponseEntity<List<FeedData>> getTechnicalFeeds() {
        return ResponseEntity.ok(newsService.getAllTechnicalFeeds());
    }
}
