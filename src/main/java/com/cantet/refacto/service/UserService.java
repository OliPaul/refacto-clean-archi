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

    public void addUser(UserModel userModel) {
        UserModel user = new UserModel(null, userModel.getName(), userModel.getEmail(), new Date(), new Date());

        userDAO.addUser(user);
    }
}
