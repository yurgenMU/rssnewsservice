package ru.otus.spring.rssnewsservice.service;

import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Component;
import ru.otus.spring.rssnewsservice.domain.feed.FeedDto;
import ru.otus.spring.rssnewsservice.domain.NewsEntryDto;
import ru.otus.spring.rssnewsservice.exception.RSSFeedRetrievalException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RSSParser {

    public FeedDto getFeedFromLink(String link)  {
        SyndFeed feed = getSyndFeed(link);
        List<SyndEntryImpl> entries = (List<SyndEntryImpl>) feed.getEntries();
        List<NewsEntryDto> newsEntries = entries.stream()
                .map(entry ->
                        new NewsEntryDto(entry.getTitle(), entry.getDescription().getValue(), entry.getLink())
                )
                .collect(Collectors.toList());
        return new FeedDto()
                .setName(feed.getTitle())
                .setDescription(feed.getDescription())
                .setEntries(newsEntries)
                .setImageSrc(feed.getImage().getLink());

    }

    private SyndFeed getSyndFeed(String link) {
        try {
            URL feedSource = new URL(link);
            SyndFeedInput input = new SyndFeedInput();
            return input.build(new XmlReader(feedSource));
        } catch (FeedException | IOException e) {
            throw new RSSFeedRetrievalException("Error while feed retrieval", e);
        }
    }
}
