package com.cantet.refacto.user.infrastructure.controller;

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
            return new ResponseEntity<>("Test user created", HttpStatus.CREATED);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Test user NOT created", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateUser(String userId, UserDto userDto) {
        try {
            userService.updateUser(userId, userDto.getName(), userDto.getEmail());
            return new ResponseEntity<>("Test user updated", HttpStatus.OK);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Test user NOT updated", HttpStatus.BAD_REQUEST);

        }

    }
}
