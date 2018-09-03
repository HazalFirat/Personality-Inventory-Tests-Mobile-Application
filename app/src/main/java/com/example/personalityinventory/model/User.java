package com.example.personalityinventory.model;

public class User {
int UserID;
String Username;
String Email;
String Password;
String UserType;

    public User() {
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        this.UserID = userID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        this.UserType = userType;
    }
}
