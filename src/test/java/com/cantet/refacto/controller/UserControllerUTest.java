package com.cantet.refacto.controller;

import com.cantet.refacto.model.UserModel;
import com.cantet.refacto.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
            final UserModel userModel = new UserModel(null, "toto", "toto@test.com", null, null);

            // when
            final ResponseEntity<String> result = userController.addUser(userModel);

            // then
            assertThat(result.getBody()).isEqualTo("Test user created");
            assertThat(result.getStatusCode()).isEqualTo(CREATED);
        }

        @Test
        void call_addUser() {
            // given
            final UserModel userModel = new UserModel(null, "toto", "toto@test.com", null, null);

            // when
            userController.addUser(userModel);

            // then
            ArgumentCaptor<UserModel> userModelArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
            verify(userService).addUser(userModelArgumentCaptor.capture());
            final UserModel expectedUser = userModelArgumentCaptor.getValue();
            assertThat(expectedUser.getUserId()).isNull();
            assertThat(expectedUser.getName()).isEqualTo("toto");
            assertThat(expectedUser.getEmail()).isEqualTo("toto@test.com");
            assertThat(expectedUser.getCreated()).isNull();
            assertThat(expectedUser.getLastConnection()).isNull();
        }
    }
}