package com.cantet.refacto.user.dao.impl;

import com.cantet.refacto.user.dao.MovementDAO;
import com.cantet.refacto.user.model.MovementModel;
import com.cantet.refacto.user.model.UserModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MongoMovementDAO implements MovementDAO {

    private final MongoTemplate mongoTemplate;

    public MongoMovementDAO(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<MovementModel> getCredits(UserModel userModel) {
        return mongoTemplate.findAll(MovementModel.class).stream()
                .filter(movementModel -> movementModel.getUserId().equals(userModel.getId()))
                .collect(Collectors.toList());
    }
}