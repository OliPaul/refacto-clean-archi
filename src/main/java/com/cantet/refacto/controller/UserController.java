package com.cantet.refacto.controller;

import com.cantet.refacto.dao.MovementDAO;
import com.cantet.refacto.model.MovementModel;
import com.cantet.refacto.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final MovementDAO movementDAO;

    public UserController(MovementDAO movementDAO) {
        this.movementDAO = movementDAO;
    }

    @GetMapping("/user/{id}/interest")
    public ResponseEntity<Float> computeInterest(@RequestBody UserModel userModel) {
        final List<MovementModel> allUserMovements = movementDAO.getCredits(userModel);

        final Float interests = allUserMovements.stream()
                .map(movementModel -> movementModel.getCredit() * 1.2f)
                .reduce(0f, Float::sum);

        return new ResponseEntity<>(interests, HttpStatus.OK);
    }
}
