package com.cantet.refacto.controller;

import com.cantet.refacto.dao.UserDAO;
import com.cantet.refacto.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    @Mock
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userController = new UserController(userDAO);
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
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }

        @Test
        void call_addUser() {
            // given
            final UserModel userModel = new UserModel(null, "toto", "toto@test.com", null, null);

            // when
            userController.addUser(userModel);

            // then
            ArgumentCaptor<UserModel> userModelArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
            verify(userDAO).addUser(userModelArgumentCaptor.capture());
            final UserModel expectedUser = userModelArgumentCaptor.getValue();
            assertThat(expectedUser.getUserId()).isNull();
            assertThat(expectedUser.getName()).isEqualTo("toto");
            assertThat(expectedUser.getEmail()).isEqualTo("toto@test.com");
            assertThat(expectedUser.getCreated()).isNotNull();
            assertThat(expectedUser.getLastConnection()).isNotNull();
        }
    }

    @Nested
    class UpdateUserShould {

        public static final String USER_ID = "21323234";
        public static final String ORIGINAL_NAME = "pouet";
        public static final String ORIGINAL_EMAIL = "pouet@test.com";
        public final Date CREATED = new Date(2019, 1, 1);
        public final Date ORIGINAL_LAST_CONNECTION = new Date(2019, 1, 1);

        @BeforeEach
        void setUp() {
            final UserModel originalSavedUser = new UserModel(USER_ID, ORIGINAL_NAME, ORIGINAL_EMAIL, CREATED, ORIGINAL_LAST_CONNECTION);
            when(userDAO.getUserById(USER_ID)).thenReturn(originalSavedUser);
        }

        @Test
        void return_ok_and_message() {
            // given
            final UserModel userModel = new UserModel(USER_ID, "toto2", "toto2@test.com", null, null);

            // when
            final ResponseEntity<String> result = userController.updateUser(userModel);

            // then
            assertThat(result.getBody()).isEqualTo("Test user updated");
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        void call_userDaoUpdateUser() {
            // given
            final String newName = "toto";
            final String newEmail = "toto@test.com";
            final UserModel newUserModel = new UserModel(USER_ID, newName, newEmail, CREATED, ORIGINAL_LAST_CONNECTION);

            // when
            userController.updateUser(newUserModel);

            // then
            ArgumentCaptor<UserModel> userModelArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
            verify(userDAO).updateUser(userModelArgumentCaptor.capture());
            final UserModel expectedUser = userModelArgumentCaptor.getValue();
            assertThat(expectedUser.getUserId()).isEqualTo(USER_ID);
            assertThat(expectedUser.getName()).isEqualTo(newName);
            assertThat(expectedUser.getEmail()).isEqualTo(newEmail);
            assertThat(expectedUser.getCreated()).isEqualTo(CREATED);
        }
    }
}