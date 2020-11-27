package com.cantet.refacto.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Movement")
@Getter
@Setter
public class MovementModel {

    @Id
    private Integer id;
    private Integer userId;
    private Float credit;

    public MovementModel(int id, Integer userId, Float credit){
        this.id = id;
        this.userId = userId;
        this.credit = credit;
    }
}
