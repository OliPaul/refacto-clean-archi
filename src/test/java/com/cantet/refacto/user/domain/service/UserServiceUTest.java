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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

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
}