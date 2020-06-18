package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateUser {
    private final UserRepository userRepository;

    public CreateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String name, String email) throws InvalidFieldException {
        User user = new User(null, name, email, new Date(), new Date());

        return userRepository.addUser(user);
    }
}
