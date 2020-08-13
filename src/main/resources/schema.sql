CREATE DATABASE IF NOT EXISTS rss_service;

CREATE TABLE IF NOT EXISTS rss_service.authorities
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS rss_service.users
(
    ID       BIGINT PRIMARY KEY AUTO_INCREMENT,
    USERNAME VARCHAR(255) UNIQUE,
    PASSWORD VARCHAR(255),
    DATE     TIMESTAMP
);

CREATE TABLE IF NOT EXISTS rss_service.users_authorities
(
    USER_ID      BIGINT,
    AUTHORITY_ID BIGINT,
    UNIQUE (USER_ID, AUTHORITY_ID)
);

CREATE TABLE rss_service.rss_feeds
(
    id   BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    name VARCHAR(100)          NOT NULL,
    link VARCHAR(1000)         NOT NULL
);

CREATE TABLE rss_service.users_feeds
(
    user_id BIGINT NOT NULL REFERENCES rss_service.users (id),
    feed_id BIGINT NOT NULL REFERENCES rss_service.rss_feeds (id),
    UNIQUE (user_id, feed_id)
)



