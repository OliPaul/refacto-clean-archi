package com.cantet.refacto.controller;

import com.cantet.refacto.dao.UserDAO;
import com.cantet.refacto.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class UserController {

    private final UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        final List<UserModel> allUsers = userDAO.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody UserModel userModel) {
        UserModel user = new UserModel(null, userModel.getName(), userModel.getEmail(), new Date(), new Date());

        userDAO.addUser(user);
        return new ResponseEntity<>("Test user created", HttpStatus.CREATED);
    }

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestBody UserModel user) {
        final UserModel userModel = userDAO.getUserById(user.getUserId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());

        userDAO.updateUser(userModel);
        return new ResponseEntity<>("Test user updated", HttpStatus.OK);
    }
}
