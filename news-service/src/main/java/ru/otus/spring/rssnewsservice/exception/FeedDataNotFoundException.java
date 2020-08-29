package ru.otus.spring.rssnewsservice.exception;

public class FeedDataNotFoundException extends RuntimeException {

    public FeedDataNotFoundException(String message) {
        super(message);
    }
}
