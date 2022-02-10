package ru.job4j.emailnotification;

public class User {
    private final String userName;
    private final String email;
    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
    public String getUserName() {
        return this.userName;
    }
    public String getEmail() {
        return this.email;
    }
}
