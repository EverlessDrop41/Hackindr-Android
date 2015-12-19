package com.everlesslycoding.hackindr.Models;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class User {
    String email;
    String password;
    String token;
    String id;

    public User(String email, String password, String token, String id) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.id = id;
    }

    public User(String email, String token, String id) {
        this.email = email;
        this.token = token;
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
