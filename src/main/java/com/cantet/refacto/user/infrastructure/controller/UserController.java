package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.use_case.CreateUser;
import com.cantet.refacto.user.use_case.UpdateUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final CreateUser createUser;
    private final UpdateUser updateUser;

    public UserController(CreateUser createUser, UpdateUser updateUser) {
        this.createUser = createUser;
        this.updateUser = updateUser;
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        try {
            createUser.execute(userDto.getName(), userDto.getEmail());
            return new ResponseEntity<>("Test user created", HttpStatus.CREATED);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Test user NOT created", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateUser(String userId, UserDto userDto) {
        try {
            updateUser.execute(userId, userDto.getName(), userDto.getEmail());
            return new ResponseEntity<>("Test user updated", HttpStatus.OK);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Test user NOT updated", HttpStatus.BAD_REQUEST);
        }

    }
}
