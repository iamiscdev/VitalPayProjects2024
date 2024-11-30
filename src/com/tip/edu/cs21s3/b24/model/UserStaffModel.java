package com.tip.edu.cs21s3.b24.model;

public class UserStaffModel {

    // Fields to store information from the users table
    private int id;
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private String role;
    private String archive;
    
    // Constructor
    public UserStaffModel(int id, String userId, String firstName, String lastName,
            String address, String password,
            String role, String archive) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.password = password;
        this.role = role;
        this.archive = archive;
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

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    public String getArchive() {
        return archive;
    }
}
