package com.tip.edu.cs21s3.b24.model;

public class UserSession {
    private static UserSession instance;

    private String username;
    private String role;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Get the instance of the singleton class
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Setters and Getters for username and role
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

