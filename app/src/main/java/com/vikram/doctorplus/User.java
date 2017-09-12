package com.vikram.doctorplus;

/**
 * Created by Vikram on 9/10/2017.
 */

public class User {
    public String username;
    public String email;
    public String password;

    public User() {
    }

    public User(String uname, String email, String pass) {
        this.username = uname;
        this.email = email;
        this.password = pass;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String uname) {
        this.username = uname;
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

    public void setPassword(String pass) {
        this.password = pass;
    }
}

