package com.cantet.refacto.infrastructure.controller;

import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.use_cases.RegisterUser;
import com.cantet.refacto.use_cases.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.CREATED;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    @Mock
    private RegisterUser registerUser;

    @Mock
    private UpdateUser updateUser;

    @BeforeEach
    void setUp() {
        userController = new UserController(updateUser, registerUser);
    }

    @Nested
    class AddUserShould {

        @Test
        void return_ok_and_message() {
            // given
            final UserDto userModel = new UserDto("toto", "toto@test.com");

            // when
            final ResponseEntity<String> result = userController.addUser(userModel);

            // then
            assertThat(result.getBody()).isEqualTo("Test user created");
            assertThat(result.getStatusCode()).isEqualTo(CREATED);
        }

        @Test
        void call_addUser() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";
            final UserDto userModel = new UserDto(name, email);

            // when
            userController.addUser(userModel);

            // then
            verify(registerUser).signIn(name, email);
        }
    }
}