package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.Movement;
import com.cantet.refacto.user.domain.MovementDAO;
import com.cantet.refacto.user.domain.User;
import com.cantet.refacto.user.use_case.ComputeInterestsImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    private ComputeInterestsImpl computeInterests;

    @Mock
    private MovementDAO movementDAO;

    @BeforeEach
    void setUp() {
        computeInterests = new ComputeInterestsImpl(movementDAO);
        userController = new UserController(computeInterests);
    }

    @Nested
    class ComputeInterestShould {

        @Test
        void return_ok_and_computed_interests() {
            // given
            final Integer userId = 42;
            final Movement movement1 = new Movement(1f);
            final Movement movement2 = new Movement(2f);
            final User user = new User(userId);
            when(movementDAO.getCredits(user)).thenReturn(asList(movement1, movement2));

            final UserDto userDto = new UserDto(userId);

            // when
            final ResponseEntity<Float> result = userController.computeInterest(userDto);

            // then
            assertThat(result.getBody()).isEqualTo(3.6000001f);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

}