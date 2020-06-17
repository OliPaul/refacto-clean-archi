package com.cantet.refacto.user.domain.service;

import com.cantet.refacto.user.dao.UserModel;
import com.cantet.refacto.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(String name, String email) throws InvalidFieldException {
        if (name.isEmpty() || email.isEmpty()) {
            throw new InvalidFieldException();
        }
        UserModel user = new UserModel(null, name, email, new Date(), new Date());

        userRepository.addUser(user);
    }
}
