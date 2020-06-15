package com.cantet.refacto.use_cases;

import com.cantet.refacto.domain.UserRepository;
import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterUser {

    private final UserRepository userRepository;

    public RegisterUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signIn(String name, String email) throws InvalidFieldException {
        User user = new User(null, name, email, new Date(), new Date());

        userRepository.add(user);
    }
}
