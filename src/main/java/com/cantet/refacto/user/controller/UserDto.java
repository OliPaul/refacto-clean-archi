package com.cantet.refacto.user.controller;

import lombok.Getter;

@Getter
public class UserDto {
    private final String name;
    private final String email;

    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
