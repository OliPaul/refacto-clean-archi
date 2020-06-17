package com.cantet.refacto.user.domain;

import com.cantet.refacto.user.dao.UserModel;

import java.util.List;

public interface UserRepository {
    UserModel addUser(UserModel user);

    List<UserModel> getAllUsers();

    UserModel getUserById(String userId);

    void updateUser(UserModel user);
}
