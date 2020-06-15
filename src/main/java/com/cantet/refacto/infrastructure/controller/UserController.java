package com.cantet.refacto.infrastructure.controller;

import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.use_cases.RegisterUser;
import com.cantet.refacto.use_cases.UpdateUser;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UpdateUser updateUser;
    private final RegisterUser registerUser;

    public UserController(UpdateUser updateUser, RegisterUser registerUser) {
        this.updateUser = updateUser;
        this.registerUser = registerUser;
    }

    @PostMapping("/user/add")
    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {
        try {
            registerUser.signIn( userDto.getName(), userDto.getEmail());
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Test user created", HttpStatus.CREATED);
    }

    @RequestMapping("/update")
    public ResponseEntity<String> updateUser(String userId) {
        try {
            updateUser.update(userId, "Test", "test@test.fr");
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Test user updated", HttpStatus.OK);
    }
}
