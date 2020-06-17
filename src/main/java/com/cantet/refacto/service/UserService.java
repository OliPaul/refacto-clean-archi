package com.cantet.refacto.service;

import com.cantet.refacto.dao.UserDAO;
import com.cantet.refacto.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addUser(String name, String email) throws InvalidFieldException {
        if (name.isEmpty() || email.isEmpty()) {
            throw new InvalidFieldException();
        }
        UserModel user = new UserModel(null, name, email, new Date(), new Date());

        userDAO.addUser(user);
    }
}
