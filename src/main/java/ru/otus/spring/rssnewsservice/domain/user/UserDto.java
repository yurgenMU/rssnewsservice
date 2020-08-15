package ru.otus.spring.rssnewsservice.domain.user;

public class UserDto {

    private String login;

    private String accessToken;

    public UserDto(String login, String accessToken) {
        this.login = login;
        this.accessToken = accessToken;
    }

    public String getLogin() {
        return login;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
