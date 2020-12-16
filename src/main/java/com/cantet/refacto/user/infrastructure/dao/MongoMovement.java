package com.cantet.refacto.user.infrastructure.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Movement")
public class MongoMovement {

    @Id
    private Integer id;
    private Integer userId;
    private Float credit;

    public MongoMovement(int id, Integer userId, Float credit){
        this.id = id;
        this.userId = userId;
        this.credit = credit;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Float getCredit() {
        return credit;
    }
}
