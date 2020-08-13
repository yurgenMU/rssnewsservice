package ru.otus.spring.rssnewsservice.domain;

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
}
