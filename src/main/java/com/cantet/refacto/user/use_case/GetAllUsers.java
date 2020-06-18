package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUsers {
    private final UserRepository userRepository;

    public GetAllUsers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> execute() throws InvalidFieldException {
        return userRepository.getAllUsers();
    }
}
