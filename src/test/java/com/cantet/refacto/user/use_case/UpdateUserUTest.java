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
    }
}