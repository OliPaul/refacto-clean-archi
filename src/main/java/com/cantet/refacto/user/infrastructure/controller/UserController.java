package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.infrastructure.model.UserModel;
import com.cantet.refacto.user.use_case.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}/interest")
    public ResponseEntity<Float> computeInterest(@RequestBody UserModel userModel) {
        final Float interests = userService.computeInterest(userModel);

        return new ResponseEntity<>(interests, HttpStatus.OK);
    }
}
