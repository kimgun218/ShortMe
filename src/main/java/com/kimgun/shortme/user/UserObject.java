package com.kimgun.shortme.user;

import java.io.UnsupportedEncodingException;

public class UserObject {
    private int id;
    private String username;
    private String password;
    private String sessionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getEncodedPassword() throws UnsupportedEncodingException {
        return password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
