package ru.otus.spring.rssnewsservice.domain.user;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.otus.spring.rssnewsservice.domain.FeedData;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private Set<Authority> authorities;

    @OneToMany(targetEntity = FeedData.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_feeds", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "feed_id"))
    private List<FeedData> userFeeds;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public List<FeedData> getUserFeeds() {
        return userFeeds;
    }
}