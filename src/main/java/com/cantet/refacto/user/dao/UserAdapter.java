package com.cantet.refacto.user.dao;

import com.cantet.refacto.user.domain.model.User;

public class UserAdapter {
    public static UserModel userToModel(User user) {
        return new UserModel(user.getUserId(), user.getName(), user.getEmail(), user.getCreated(), user.getLastConnection());
    }

    public static User modelToUser(UserModel userModel) {
        return new User(userModel.getUserId(), userModel.getName(), userModel.getEmail(), userModel.getCreated(), userModel.getLastConnection());
    }
}
