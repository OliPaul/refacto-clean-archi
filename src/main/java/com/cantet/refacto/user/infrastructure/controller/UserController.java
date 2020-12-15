package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.service.User;
import com.cantet.refacto.user.use_case.ComputeInterests;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final ComputeInterests computeInterests;

    public UserController(ComputeInterests computeInterests) {
        this.computeInterests = computeInterests;
    }

    @GetMapping("/user/{id}/interest")
    public ResponseEntity<Float> computeInterest(@RequestBody UserDto userDto) {
        final User user = new User(userDto.getId());
        final Float interests = computeInterests.execute(user);

        return new ResponseEntity<>(interests, HttpStatus.OK);
    }
}
