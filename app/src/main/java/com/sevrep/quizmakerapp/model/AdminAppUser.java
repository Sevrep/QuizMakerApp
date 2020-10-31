package com.sevrep.quizmakerapp.model;

public class AdminAppUser {

    private String fullname;
    private String username;
    private String password;
    private String type;

    public AdminAppUser(String fullname, String username, String password, String type) {
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

}
