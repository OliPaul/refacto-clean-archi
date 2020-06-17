package com.cantet.refacto.user.domain;

import com.cantet.refacto.user.domain.model.User;

import java.util.List;

public interface UserRepository {
    User addUser(User user);

    List<User> getAllUsers();

    User getUserById(String userId);

    void updateUser(User user);
}
