package com.cantet.refacto.controller;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    @Mock
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        userController = new UserController(userServiceImpl);
    }

    @Nested
    class ComputeInterestShould {

        @Test
        void return_ok_and_computed_interests() {
            // given
            final Integer userId = 42;
            final UserModel userModel = new UserModel(userId);
            final float expectedInterest = 3.6f;
            when(userServiceImpl.computeInterest(userModel)).thenReturn(expectedInterest);

            // when
            final ResponseEntity<Float> result = userController.computeInterest(userModel);

            // then
            assertThat(result.getBody()).isEqualTo(expectedInterest);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

}