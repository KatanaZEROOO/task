package com.example.task;

public class Users {
    private String username;
    private String password;
    private static String currentUser;

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static void setCurrentUser(String user) {
        currentUser = user;
    }
    public static String getCurrentUser() {
        return currentUser;
    }
}
