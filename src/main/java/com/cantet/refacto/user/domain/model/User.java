package com.cantet.refacto.user.domain.model;

import java.util.Date;

public class User {

    private final String userId;
    private final String name;
    private final String email;
    private final Date created;
    private final Date lastConnection;

    public User(String userId, String name, String email, Date created, Date lastConnection){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.created = created;
        this.lastConnection = lastConnection;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreated() {
        return created;
    }

    public Date getLastConnection() {
        return lastConnection;
    }
}
