package com.cantet.refacto.use_cases;

import com.cantet.refacto.domain.UserRepository;
import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class UpdateUser {
    private final UserRepository userRepository;

    public UpdateUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void update(String userId, String name, String email)  throws InvalidFieldException {
        final User user = userRepository.getUserById(userId);

        user.update(name, email);

        userRepository.updateUser(user);
    }
}
