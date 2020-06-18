package com.cantet.refacto.user.domain.service;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return null;
    }

    public User addUser(String name, String email) throws InvalidFieldException {
        User user = new User(null, name, email, new Date(), new Date());

        return userRepository.addUser(user);
    }

    public void updateUser(String userId, String name, String email) throws InvalidFieldException {
        final User existingUser = userRepository.getUserById(userId);

        final User updatedUser = new User(userId, name, email, existingUser.getCreated(), new Date());

        userRepository.updateUser(updatedUser);
    }
}
