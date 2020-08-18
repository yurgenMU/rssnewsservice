package ru.otus.spring.rssnewsservice.controller;

import com.sun.syndication.io.FeedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public List<FeedDto> getFeed() throws IOException, FeedException {
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

}
