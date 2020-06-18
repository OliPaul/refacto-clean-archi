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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUTest {

    private UpdateUser updateUser;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        updateUser = new UpdateUser(userRepository);
    }

    @Nested
    class ExecuteShould {

        private String userId;
        private Date created;
        private Date originalLastConnection;

        @BeforeEach
        void setUp() throws InvalidFieldException {
            userId = "21323234";
            String originalName = "pouet";
            String originalEmail = "pouet@test.com";
            created = new Date(2019, 1, 1);
            originalLastConnection = new Date(2019, 1, 1);

            final User originalSavedUser = new User(userId, originalName, originalEmail, created, originalLastConnection);
            when(userRepository.getUserById(userId)).thenReturn(originalSavedUser);
        }

        @Test
        void call_userDaoUpdateUser() throws InvalidFieldException {
            // given
            final String newName = "toto";
            final String newEmail = "toto@test.com";

            // when
            updateUser.execute(userId, newName, newEmail);

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

        @Test
        void throw_InvalidFieldException_when_name_is_empty() {
            // when
            final Throwable throwable = catchThrowable(() -> updateUser.execute(userId, "", "toto@test.com"));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }

        @Test
        void throw_InvalidFieldException_when_email_is_empty() {// given
            // when
            final Throwable throwable = catchThrowable(() -> updateUser.execute(userId, "toto", ""));

            // then
            assertThat(throwable).isInstanceOf(InvalidFieldException.class);
        }
    }

}