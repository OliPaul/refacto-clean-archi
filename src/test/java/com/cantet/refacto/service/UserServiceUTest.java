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
        UserModel userModel = new UserModel(null, "toto", "toto@test.com", null, null);

        // when
        userService.addUser(userModel);

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