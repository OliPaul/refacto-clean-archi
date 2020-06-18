package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.use_case.CreateUser;
import com.cantet.refacto.user.use_case.GetAllUsers;
import com.cantet.refacto.user.use_case.UpdateUser;
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

    private final GetAllUsers getAllUsers;
    private final CreateUser createUser;
    private final UpdateUser updateUser;

    public UserController(GetAllUsers getAllUsers, CreateUser createUser, UpdateUser updateUser) {
        this.getAllUsers = getAllUsers;
        this.createUser = createUser;
        this.updateUser = updateUser;
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers() throws InvalidFieldException {
        final List<User> allUsers = getAllUsers.execute();
        final List<UserDto> userDtos = allUsers.stream()
                .map(currentUser -> new UserDto(currentUser.getName(), currentUser.getEmail()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
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

    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDto user) {
        try {
            updateUser.execute(user.getUserId(), user.getName(), user.getEmail());
            return new ResponseEntity<>("Test user updated", HttpStatus.OK);
        } catch (InvalidFieldException e) {
            return new ResponseEntity<>("Test user NOT updated", HttpStatus.BAD_REQUEST);
        }
    }
}
