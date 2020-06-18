package com.cantet.refacto.user.infrastructure.dao;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;

public class UserAdapter {
    public static MongoUser userToModel(User user) {
        return new MongoUser(user.getUserId(), user.getName(), user.getEmail(), user.getCreated(), user.getLastConnection());
    }

    public static User modelToUser(MongoUser mongoUser) throws InvalidFieldException {
        return new User(mongoUser.getUserId(), mongoUser.getName(), mongoUser.getEmail(), mongoUser.getCreated(), mongoUser.getLastConnection());
    }
}
