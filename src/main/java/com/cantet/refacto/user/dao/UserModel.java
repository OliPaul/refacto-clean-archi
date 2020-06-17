package com.cantet.refacto.user.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "User")
@Getter
@Setter
public class UserModel {

    @Id
    private String userId;
    private String name;
    private String email;
    private Date created;
    private Date lastConnection;

    public UserModel(String userId, String name, String email, Date created, Date lastConnection){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.created = created;
        this.lastConnection = lastConnection;
    }
}
