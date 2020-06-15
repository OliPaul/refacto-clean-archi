package com.cantet.refacto.infrastructure.dao;

import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.domain.model.User;

public class UserAdapter {

    public static User convertToUser(MongoUser mongoUser) throws InvalidFieldException {
        return new User(mongoUser.getUserId(),
                mongoUser.getName(),
                mongoUser.getEmail(),
                mongoUser.getCreated(),
                mongoUser.getLastConnection()
        );
    }

    public static MongoUser convertToUserModel(User user) {
        return new MongoUser(user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getCreated(),
                user.getLastConnection()
        );
    }
}
