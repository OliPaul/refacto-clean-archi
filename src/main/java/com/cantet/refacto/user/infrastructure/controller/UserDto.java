package com.cantet.refacto.user.infrastructure.controller;

import lombok.Getter;

@Getter
public class UserDto {
    private String id;
    private final String name;
    private final String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public UserDto(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return id;
    }
}
