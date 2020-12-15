package com.cantet.refacto.user.infrastructure.dao.impl;

import com.cantet.refacto.user.domain.service.Movement;
import com.cantet.refacto.user.domain.service.MovementDAO;
import com.cantet.refacto.user.domain.service.User;
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
        final List<MongoMovement> mongoMovements = mongoTemplate.findAll(MongoMovement.class).stream()
                .filter(mongoMovement -> mongoMovement.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        final List<Movement> movements = mongoMovements
                .stream()
                .map(mongoMovement -> new Movement(mongoMovement.getCredit()))
                .collect(Collectors.toList());

        return movements;
    }
}
