package com.cantet.refacto.controller;

import com.cantet.refacto.dao.UserDAO;
import com.cantet.refacto.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody UserModel userModel) {
        UserModel user = new UserModel(null, userModel.getName(), userModel.getEmail(), new Date(), new Date());

        userDAO.addUser(user);
        return new ResponseEntity<>("Test user created", HttpStatus.CREATED);
    }
}
