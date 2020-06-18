package com.cantet.refacto.user.domain;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {
    User addUser(User user) throws InvalidFieldException;

    List<User> getAllUsers() throws InvalidFieldException;

    User getUserById(String userId) throws InvalidFieldException;

    void updateUser(User user);
}
