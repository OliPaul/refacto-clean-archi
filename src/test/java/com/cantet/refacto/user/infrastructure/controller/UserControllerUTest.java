package com.cantet.refacto.user.infrastructure.controller;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.use_case.CreateUser;
import com.cantet.refacto.user.use_case.GetAllUsers;
import com.cantet.refacto.user.use_case.UpdateUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class UserControllerUTest {

    private UserController userController;

    @Mock
    private GetAllUsers getAllUsers;

    @Mock
    private CreateUser createUser;

    @Mock
    private UpdateUser updateUser;

    @BeforeEach
    void setUp() {
        userController = new UserController(getAllUsers, createUser, updateUser);
    }

    @Nested
    class GetUsesShould {
        @Test
        void return_ok_and_existing_users() throws InvalidFieldException {
            // given
            final User user = new User(null, "toto", "toto@test.com", null, null);
            final List<User> users = singletonList(user);
            when(getAllUsers.execute()).thenReturn(users);

            // when
            final ResponseEntity<List<UserDto>> result = userController.getAllUsers();

            // then
            final List<UserDto> expectedUserDtos = users.stream()
                    .map(currentUser -> new UserDto(currentUser.getName(), currentUser.getEmail()))
                    .collect(Collectors.toList());
            assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparator().isEqualTo(expectedUserDtos);
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
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

        public static final String USER_ID = "21323234";

        @Test
        void return_ok_and_message() {
            // given
            final UserDto userModel = new UserDto(USER_ID, "toto2", "toto2@test.com");

            // when
            final ResponseEntity<String> result = userController.updateUser(userModel);

            // then
            assertThat(result.getBody()).isEqualTo("Test user updated");
            assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        void call_updateUser() throws InvalidFieldException {
            // given
            final String newName = "toto";
            final String newEmail = "toto@test.com";
            final UserDto newUserModel = new UserDto(USER_ID, newName, newEmail);

            // when
            userController.updateUser(newUserModel);

            // then
            verify(updateUser).execute(USER_ID, newName, newEmail);
        }

        @Test
        void return_badRequest_when_addUser_throw_InvalidFieldException() throws InvalidFieldException {
            // given
            final String name = "";
            final String email = "";

            final UserDto userDto = new UserDto(USER_ID, name, email);

            doThrow(new InvalidFieldException()).when(updateUser).execute(USER_ID, name, email);

            // when
            final ResponseEntity<String> responseEntity = userController.updateUser(userDto);

            // then
            assertThat(responseEntity.getBody()).isEqualTo("Test user NOT updated");
            assertThat(responseEntity.getStatusCode()).isEqualTo(BAD_REQUEST);
        }
    }
}