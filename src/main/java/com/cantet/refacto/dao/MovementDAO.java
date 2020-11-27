package com.cantet.refacto.dao;

import com.cantet.refacto.model.MovementModel;
import com.cantet.refacto.model.UserModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovementDAO {

    private final MongoTemplate mongoTemplate;

    public MovementDAO(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<MovementModel> getCredits(UserModel userModel) {
        return mongoTemplate.findAll(MovementModel.class).stream()
                .filter(movementModel -> movementModel.getUserId().equals(userModel.getId()))
                .collect(Collectors.toList());
    }
}
