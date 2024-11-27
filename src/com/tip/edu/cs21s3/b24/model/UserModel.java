package com.tip.edu.cs21s3.b24.model;

public class UserModel {

    // Fields to store information from the users table
    private int id;
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    private String username;
    private String password;
    private String role;

    // Constructor
    public UserModel(int id, String userId, String firstName, String lastName,
            String address, String username, String password,
            String role) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }


    // Override toString() for better representation
    @Override
    public String toString() {
        return "UserModel{"
                + "id=" + id
                + ", userId='" + userId + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", address='" + address + '\''
                + ", username='" + username + '\''
                + ", password='" + password + '\''
                + ", role='" + role + '\''
                + '}';
    }
}
