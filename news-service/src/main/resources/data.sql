INSERT INTO rss_service.AUTHORITIES (id, name)
VALUES (1, 'ADMIN');
INSERT INTO rss_service.AUTHORITIES (id, name)
VALUES (2, 'USER');
INSERT INTO rss_service.USERS_AUTHORITIES (user_id, authority_id)
VALUES (1, 1);
INSERT INTO rss_service.USERS_AUTHORITIES (user_id, authority_id)
VALUES (2, 2);
INSERT INTO rss_service.USERS (ID, USERNAME, PASSWORD, DATE)
# VALUES (1, 'Ivan', '$2y$12$kWbvXPvhPNHoEIdlwHfEsOS3EjkdJNTZNaF3AiBwbRwZ1.PIS4m9G', '2019-07-03 12:37:25');
# INSERT INTO rss_service.USERS (ID, USERNAME, PASSWORD, DATE)
# VALUES (2, 'Petr', '$2y$12$kWbvXPvhPNHoEIdlwHfEsOS3EjkdJNTZNaF3AiBwbRwZ1.PIS4m9G', '2019-11-15 22:14:54');

INSERT INTO rss_service.USERS (ID, USERNAME, PASSWORD)
VALUES (1, 'admin', '$2y$12$kWbvXPvhPNHoEIdlwHfEsOS3EjkdJNTZNaF3AiBwbRwZ1.PIS4m9G');

INSERT INTO rss_service.rss_feeds
VALUES (1, 'yandex sport', 'https://news.yandex.ru/sport.rss');
INSERT INTO rss_service.rss_feeds
VALUES (2, 'yandex movies', 'https://news.yandex.ru/movies.rss');


INSERT INTO rss_service.users_feeds
VALUES (1, 1);
INSERT INTO rss_service.users_authorities
VALUES (1, 1);
INSERT INTO rss_service.users_authorities
VALUES (2, 1);