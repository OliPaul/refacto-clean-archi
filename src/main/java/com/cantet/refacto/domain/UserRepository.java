package com.cantet.refacto.domain;

import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.domain.model.User;

public interface UserRepository {
    User add(User user) throws InvalidFieldException;

    User getUserById(String userId) throws InvalidFieldException;

    void updateUser(User userModel);
}
