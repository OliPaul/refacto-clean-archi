package com.cantet.refacto.user.controller;

import com.cantet.refacto.user.domain.service.InvalidFieldException;
import com.cantet.refacto.user.domain.service.UserService;
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
    private UserService userService;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Nested
    class AddUserShould {

        @Test
        void return_ok_and_message() {
            // given
            final UserDto userDto = new UserDto("toto", "toto@test.com");

            // when
            final ResponseEntity<String> result = userController.addUser(userDto);

            // then
            assertThat(result.getBody()).isEqualTo("Test user created");
            assertThat(result.getStatusCode()).isEqualTo(CREATED);
        }

        @Test
        void call_addUser() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";
            final UserDto userDto = new UserDto(name, email);

            // when
            userController.addUser(userDto);

            // then
            verify(userService).addUser(name, email);
        }
    }
}