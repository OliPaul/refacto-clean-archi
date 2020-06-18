package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.use_case.CreateUser;
import com.cantet.refacto.user.use_case.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    @Mock
    private CreateUser createUser;

    @Mock
    private UpdateUser updateUser;

    @BeforeEach
    void setUp() {
        userController = new UserController(createUser, updateUser);
    }

    @Nested
    class AddUserShould {

        @Test
        void return_create_and_message() {
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
            verify(createUser).execute(name, email);
        }

        @Test
        void return_badRequest_when_addUser_throw_InvalidFieldException() throws InvalidFieldException {
            // given
            final String name = "";
            final String email = "";

            final UserDto userDto = mock(UserDto.class);
            when(userDto.getName()).thenReturn(name);
            when(userDto.getEmail()).thenReturn(email);

            doThrow(new InvalidFieldException()).when(createUser).execute(name, email);

            // when
            final ResponseEntity<String> responseEntity = userController.addUser(userDto);

            // then
            assertThat(responseEntity.getBody()).isEqualTo("Test user NOT created");
            assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
        }
    }

    @Nested
    class UpdateUserShould {

        @Test
        void return_ok_and_message() {
            // given
            final String userId = "23424'";
            final UserDto userDto = new UserDto("toto", "toto@test.com");

            // when
            final ResponseEntity<String> result = userController.updateUser(userId, userDto);

            // then
            assertThat(result.getBody()).isEqualTo("Test user updated");
            assertThat(result.getStatusCode()).isEqualTo(OK);
        }

        @Test
        void call_updateUser() throws InvalidFieldException {
            // given
            final String userId = "23434";
            final String name = "toto";
            final String email = "toto@test.com";
            final UserDto userDto = new UserDto(name, email);

            // when
            userController.updateUser(userId, userDto);

            // then
            verify(updateUser).execute(userId, name, email);
        }

        @Test
        void return_badRequest_when_updateUser_throw_InvalidFieldException() throws InvalidFieldException {
            // given
            final String userId = "213234234";
            final String name = "";
            final String email = "";

            final UserDto userDto = mock(UserDto.class);
            when(userDto.getName()).thenReturn(name);
            when(userDto.getEmail()).thenReturn(email);

            doThrow(new InvalidFieldException()).when(updateUser).execute(userId, name, email);

            // when
            final ResponseEntity<String> responseEntity = userController.updateUser(userId, userDto);

            // then
            assertThat(responseEntity.getBody()).isEqualTo("Test user NOT updated");
            assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
        }
    }
}