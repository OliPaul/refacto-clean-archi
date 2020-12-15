package com.cantet.refacto.user.infrastructure.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class UserModel {

    @Id
    private Integer id;

    public UserModel(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
