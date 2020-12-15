package com.cantet.refacto.user.infrastructure.controller;

public class UserDto {
    private Integer id;

    public UserDto(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

}
