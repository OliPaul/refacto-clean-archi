package com.cantet.refacto.user.infrastructure.dao.impl;

import com.cantet.refacto.user.domain.service.Movement;
import com.cantet.refacto.user.domain.service.MovementDAO;
import com.cantet.refacto.user.domain.service.User;
import com.cantet.refacto.user.infrastructure.model.MovementModel;
import com.cantet.refacto.user.infrastructure.model.UserModel;
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
    public List<Movement> getCredits(User user) {
        final List<MovementModel> movementModels = mongoTemplate.findAll(MovementModel.class).stream()
                .filter(movementModel -> movementModel.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        final List<Movement> movements = movementModels
                .stream()
                .map(movementModel -> new Movement(movementModel.getCredit()))
                .collect(Collectors.toList());

        return movements;
    }
}
