package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.service.InvalidFieldException;
import com.cantet.refacto.user.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        final List<User> allUsers = userService.getAllUsers();
        final List<UserDto> userDtos = allUsers.stream()
                .map(currentUser -> new UserDto(currentUser.getName(), currentUser.getEmail()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
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

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto user) {
        try {
            userService.updateUser(user.getUserId(), user.getName(), user.getEmail());
            return new ResponseEntity<>("Test user updated", HttpStatus.OK);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Test user NOT updated", HttpStatus.BAD_REQUEST);
        }
    }
}
