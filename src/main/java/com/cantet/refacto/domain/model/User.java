package com.cantet.refacto.domain.model;

import java.util.Date;

import static org.junit.platform.commons.util.StringUtils.isBlank;

public class User {

    private String userId;
    private String name;
    private String email;
    private Date created;
    private Date lastConnection;

    public User(String userId, String name, String email, Date created, Date lastConnection) throws InvalidFieldException {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.created = created;
        this.lastConnection = lastConnection;
        checkUser();
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

    public void update(String name, String email) throws InvalidFieldException {
        this.name = name;
        this.email = email;
        this.checkUser();
    }

    private void checkUser() throws InvalidFieldException {
        if (isBlank(name) || isBlank(email)) {
            throw new InvalidFieldException();
        }
    }
}
