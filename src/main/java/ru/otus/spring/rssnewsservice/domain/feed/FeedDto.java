package ru.otus.spring.rssnewsservice.domain.feed;

import ru.otus.spring.rssnewsservice.domain.NewsEntryDto;

import java.util.List;

public class FeedDto {

    private Long id;
    private String name;
    private String description;
    private List<NewsEntryDto> entries;
    private String imageSrc;

    public Long getId() {
        return id;
    }

    public FeedDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public FeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FeedDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<NewsEntryDto> getEntries() {
        return entries;
    }

    public FeedDto setEntries(List<NewsEntryDto> entries) {
        this.entries = entries;
        return this;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public FeedDto setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
        return this;
    }
}
