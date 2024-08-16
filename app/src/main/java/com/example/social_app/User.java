package com.example.social_app;

public class User {
    private long id;
    private String userName;
    private String userPhoneNumber;
    private String userEmailAddress;
    private String userPassword;

    public User(long id, String userName, String userPhoneNumber, String userEmailAddress, String userPassword) {
        this.id = id;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmailAddress = userEmailAddress;
        this.userPassword = userPassword;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(String userName, String userPhoneNumber, String userEmailAddress, String userPassword) {
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userEmailAddress = userEmailAddress;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
