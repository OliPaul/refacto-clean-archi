package com.cantet.refacto.controller;

import com.cantet.refacto.dao.MovementDAO;
import com.cantet.refacto.model.MovementModel;
import com.cantet.refacto.model.UserModel;
import com.cantet.refacto.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    private UserServiceImpl userServiceImpl;

    @Mock
    private MovementDAO movementDAO;

    @BeforeEach
    void setUp() {
        userServiceImpl = new UserServiceImpl(movementDAO);
        userController = new UserController(userServiceImpl);
    }

    @Nested
    class ComputeInterestShould {

        @Test
        void return_ok_and_computed_interests() {
            // given
            final Integer userId = 42;
            final UserModel userModel = new UserModel(userId);
            final MovementModel movementModel1 = new MovementModel(1, userId, 1f);
            final MovementModel movementModel2 = new MovementModel(2, userId, 2f);
            when(movementDAO.getCredits(userModel)).thenReturn(asList(movementModel1, movementModel2));

            // when
            final ResponseEntity<Float> result = userController.computeInterest(userModel);

            // then
            assertThat(result.getBody()).isEqualTo(3.6000001f);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

}