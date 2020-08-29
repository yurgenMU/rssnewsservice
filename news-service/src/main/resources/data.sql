INSERT INTO rss_service.authorities (id, name)
VALUES (1, 'ADMIN');
INSERT INTO rss_service.authorities (id, name)
VALUES (2, 'USER');


INSERT INTO rss_service.users (ID, USERNAME, PASSWORD)
VALUES (1, 'admin', '$2y$12$kWbvXPvhPNHoEIdlwHfEsOS3EjkdJNTZNaF3AiBwbRwZ1.PIS4m9G');

INSERT INTO rss_service.rss_feeds
VALUES (1, 'yandex sport', 'https://news.yandex.ru/sport.rss');
INSERT INTO rss_service.rss_feeds
VALUES (2, 'yandex movies', 'https://news.yandex.ru/movies.rss');

INSERT INTO rss_service.users_feeds
VALUES (1, 1);
INSERT INTO rss_service.users_authorities
VALUES (1, 1);