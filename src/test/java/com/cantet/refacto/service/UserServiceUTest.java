package com.cantet.refacto.service;

import com.cantet.refacto.dao.UserDAO;
import com.cantet.refacto.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceUTest {

    private UserService userService;

    @Mock
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userService = new UserService(userDAO);
    }

    @Test
    void addUser_should_call_userDaoAddUser() {
        // given
        final String name = "toto";
        final String email = "toto@test.com";
        UserModel userModel = new UserModel(null, name, email, null, null);

        // when
        userService.addUser(name, email);

        // then
        ArgumentCaptor<UserModel> userModelArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
        verify(userDAO).addUser(userModelArgumentCaptor.capture());
        final UserModel expectedUser = userModelArgumentCaptor.getValue();
        assertThat(expectedUser.getUserId()).isNull();
        assertThat(expectedUser.getName()).isEqualTo(name);
        assertThat(expectedUser.getEmail()).isEqualTo(email);
        assertThat(expectedUser.getCreated()).isNotNull();
        assertThat(expectedUser.getLastConnection()).isNotNull();
    }
}