package com.cantet.refacto.user.dao;


import com.cantet.refacto.user.domain.model.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserAdapterUTest {
    @Test
    void userToModel_should_convert_User_into_UserModel() {
        // given
        final Date created = new Date();
        final Date lastConnection = new Date();
        User user = new User("1232132", "toto", "toto@test.com", created, lastConnection);
        UserModel expectedUserModel = new UserModel("1232132", "toto", "toto@test.com", created, lastConnection);

        // when
        UserModel userModel = UserAdapter.userToModel(user);

        // then
        assertThat(userModel).isEqualToComparingFieldByField(expectedUserModel);
    }

    @Test
    void modelToUser_should_convert_UserModel_into_User() {
        // given
        final Date created = new Date();
        final Date lastConnection = new Date();
        UserModel userModel = new UserModel("1232132", "toto", "toto@test.com", created, lastConnection);
        User expectedUser = new User("1232132", "toto", "toto@test.com", created, lastConnection);

        // when
        User user = UserAdapter.modelToUser(userModel);

        // then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }
}