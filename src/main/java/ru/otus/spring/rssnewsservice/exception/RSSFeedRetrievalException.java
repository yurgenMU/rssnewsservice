package ru.otus.spring.rssnewsservice.exception;

public class RSSFeedRetrievalException extends RuntimeException {

    public RSSFeedRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
