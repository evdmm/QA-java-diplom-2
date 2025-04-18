package ru.yandex.praktikum;

import java.util.concurrent.ThreadLocalRandom;

public class User {

    private String email;
    private String password;
    private String name;
    private String accessToken;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User() {

    }

    public static String generateRandomEmail() {
        int suffix = ThreadLocalRandom.current().nextInt(1, 100_000);
        String emailPostfix = "@yandex.ru";
        return "Bear" + suffix + emailPostfix;
    }

    public static String generateRandomPassword() {
        int suffix = ThreadLocalRandom.current().nextInt(1, 100_000);
        return "Passw@rd" + suffix;
    }

    public static String generateRandomName() {
        int suffix = ThreadLocalRandom.current().nextInt(1, 100_000);
        return "Paddington" + suffix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
