package com.cantet.refacto.user.controller;

import com.cantet.refacto.user.domain.service.InvalidFieldException;
import com.cantet.refacto.user.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}
