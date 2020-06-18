package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UpdateUser {
    private final UserRepository userRepository;

    public UpdateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String userId, String name, String email) throws InvalidFieldException {
        final User existingUser = userRepository.getUserById(userId);

        final User updatedUser = new User(userId, name, email, existingUser.getCreated(), new Date());

        userRepository.updateUser(updatedUser);
    }
}
