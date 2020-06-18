package com.cantet.refacto.user.domain.service;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }

    @Nested
    class AddUserShould {

        @Test
        void return_created_user() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";
            final String userId = "132234";

            final User expectedUser = new User(userId, name, email, new Date(), new Date());
            when(userRepository.addUser(any(User.class))).thenReturn(expectedUser);

            // when
            User createdUser = userService.addUser(name, email);

            // then
            assertThat(createdUser).isEqualToComparingFieldByField(expectedUser);
            assertThat(createdUser.getUserId()).isEqualTo(userId);
            assertThat(createdUser.getName()).isEqualTo(name);
            assertThat(createdUser.getEmail()).isEqualTo(email);
            assertThat(createdUser.getCreated()).isNotNull();
            assertThat(createdUser.getLastConnection()).isNotNull();
        }

        @Test
        void call_userDaoAddUser() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";

            // when
            userService.addUser(name, email);

            // then
            ArgumentCaptor<User> userModelArgumentCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).addUser(userModelArgumentCaptor.capture());
            final User expectedUser = userModelArgumentCaptor.getValue();
            assertThat(expectedUser.getUserId()).isNull();
            assertThat(expectedUser.getName()).isEqualTo(name);
            assertThat(expectedUser.getEmail()).isEqualTo(email);
            assertThat(expectedUser.getCreated()).isNotNull();
            assertThat(expectedUser.getLastConnection()).isNotNull();
        }

        @Test
        void throw_InvalidFieldException_when_name_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> userService.addUser("", "toto@test.com"));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }

        @Test
        void throw_InvalidFieldException_when_email_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> userService.addUser("toto", ""));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }
    }

    @Nested
    class UpdateUserShould {

        @Test
        void call_userDaoUpdateUser() throws InvalidFieldException {
            // given
            final String userId = "21323234";
            final String originalName = "pouet";
            final String originalEmail = "pouet@test.com";
            final Date created = new Date(2019, 1, 1);
            final Date originalLastConnection = new Date(2019, 1, 1);

            final User originalSavedUser = new User(userId, originalName, originalEmail, created, originalLastConnection);
            when(userRepository.getUserById(userId)).thenReturn(originalSavedUser);

            final String newName = "toto";
            final String newEmail = "toto@test.com";

            // when
            userService.updateUser(userId, newName, newEmail);

            // then
            ArgumentCaptor<User> userModelArgumentCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).updateUser(userModelArgumentCaptor.capture());
            final User expectedUser = userModelArgumentCaptor.getValue();
            assertThat(expectedUser.getUserId()).isEqualTo(userId);
            assertThat(expectedUser.getName()).isEqualTo(newName);
            assertThat(expectedUser.getEmail()).isEqualTo(newEmail);
            assertThat(expectedUser.getCreated()).isEqualTo(created);
            assertThat(expectedUser.getLastConnection().before(originalLastConnection)).isTrue();
        }
    }
}