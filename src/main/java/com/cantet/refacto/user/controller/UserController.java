package com.cantet.refacto.user.controller;

import com.cantet.refacto.user.domain.service.InvalidFieldException;
import com.cantet.refacto.user.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import java.util.Date;
import java.util.List;

=======
>>>>>>> create userDto and use it in controller, and change addUser signature in service
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        final List<UserModel> allUsers = userDAO.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        try {
            userService.addUser(userDto.getName(), userDto.getEmail());
        } catch (InvalidFieldException e) {
            e.printStackTrace();
        }
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
