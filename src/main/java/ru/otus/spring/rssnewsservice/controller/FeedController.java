package ru.otus.spring.rssnewsservice.controller;

import com.sun.syndication.io.FeedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.rssnewsservice.domain.FeedDto;
import ru.otus.spring.rssnewsservice.service.NewsService;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
public class FeedController {

    private final NewsService newsService;

    public FeedController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/ping")
    public List<FeedDto> getFeed() throws IOException, FeedException {
        return newsService.getAll();
    }

    @GetMapping("/feed")
    public List<FeedDto> getFeedForUser(Principal principal) throws IOException, FeedException {
        String username = principal.getName();
        return newsService.getNewsForUser(username);
    }
}
