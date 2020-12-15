package com.cantet.refacto.user.infrastructure.dao.impl;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class MongoUser {

    @Id
    private Integer id;

    public MongoUser(int id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
