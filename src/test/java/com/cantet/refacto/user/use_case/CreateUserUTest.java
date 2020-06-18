package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
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
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserUTest {

    private CreateUser createUser;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        createUser = new CreateUser(userRepository);
    }

    @Nested
    class ExecuteShould {

        @Test
        void return_created_user() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";
            final String userId = "132234";

            final User expectedUser = new User(userId, name, email, new Date(), new Date());
            when(userRepository.addUser(any(User.class))).thenReturn(expectedUser);

            // when
            User createdUser = createUser.execute(name, email);

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
            createUser.execute(name, email);

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
            final Throwable throwable = catchThrowable(() -> createUser.execute("", "toto@test.com"));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }

        @Test
        void throw_InvalidFieldException_when_email_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> createUser.execute("toto", ""));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }
    }
}