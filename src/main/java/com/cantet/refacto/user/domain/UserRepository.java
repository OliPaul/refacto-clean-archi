package com.cantet.refacto.user.domain;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;

import java.util.List;

public interface UserRepository {
    User addUser(User user) throws InvalidFieldException;

    List<User> getAllUsers() throws InvalidFieldException;

    User getUserById(String userId) throws InvalidFieldException;

    void updateUser(User user);
}
