package ru.otus.spring.rssnewsservice.domain.feed;

import javax.persistence.*;

@Entity
@Table(name = "rss_feeds")
public class FeedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    public FeedData() {
    }

    public FeedData(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public FeedData setId(Long id) {
        this.id = id;
        return this;
    }

    public FeedData setName(String name) {
        this.name = name;
        return this;
    }

    public FeedData setLink(String link) {
        this.link = link;
        return this;
    }
}
