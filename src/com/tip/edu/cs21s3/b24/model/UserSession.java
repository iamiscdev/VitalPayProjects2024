package com.tip.edu.cs21s3.b24.model;

public class UserSession {
    private static UserSession instance;

    private String userId;
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
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

