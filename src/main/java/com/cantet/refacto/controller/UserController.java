package com.cantet.refacto.controller;

import com.cantet.refacto.service.UserService;
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
        userService.addUser(userDto.getName(), userDto.getEmail());
        return new ResponseEntity<>("Test user created", HttpStatus.CREATED);
    }
}
