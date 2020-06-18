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

import java.sql.Date;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

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

        public static final String USER_ID = "123123";

        @BeforeEach
        void setUp() throws InvalidFieldException {
            User existingUser = new User(USER_ID, "old name", "old email", Date.valueOf("2020-11-01"), Date.valueOf("2020-11-01"));
            when(userRepository.getUserById(USER_ID)).thenReturn(existingUser);
        }

        @Test
        void call_userDaoUpdateUser() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";

            // when
            userService.updateUser(USER_ID, name, email);

            // then
            ArgumentCaptor<User> userModelArgumentCaptor = ArgumentCaptor.forClass(User.class);
            verify(userRepository).updateUser(userModelArgumentCaptor.capture());
            final User expectedUser = userModelArgumentCaptor.getValue();
            assertThat(expectedUser.getUserId()).isEqualTo(USER_ID);
            assertThat(expectedUser.getName()).isEqualTo(name);
            assertThat(expectedUser.getEmail()).isEqualTo(email);
            assertThat(expectedUser.getCreated()).isNotNull();
            assertThat(expectedUser.getLastConnection()).isNotNull();
        }

        @Test
        void throw_InvalidFieldException_when_name_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> userService.updateUser(USER_ID, "", "toto@test.com"));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }

        @Test
        void throw_InvalidFieldException_when_email_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> userService.updateUser(USER_ID, "toto", ""));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }
    }
}