package com.cantet.refacto.user.use_case;


import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import com.cantet.refacto.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUTest {
    private GetAllUsers createUser;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        createUser = new GetAllUsers(userRepository);
    }

    @Nested
    class ExecuteShould {

        @Test
        void return_all_users() throws InvalidFieldException {
            // given
            final String name = "toto";
            final String email = "toto@test.com";
            final String userId = "132234";

            final User expectedUser = new User(userId, name, email, new Date(), new Date());
            when(userRepository.getAllUsers()).thenReturn(Collections.singletonList(expectedUser));

            // when
            List<User> users = createUser.execute();

            // then
            assertThat(users).usingRecursiveFieldByFieldElementComparator().containsOnly(expectedUser);
        }
    }
}