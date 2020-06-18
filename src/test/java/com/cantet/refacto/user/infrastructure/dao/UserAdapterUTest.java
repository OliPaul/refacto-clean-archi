package com.cantet.refacto.user.infrastructure.dao;


import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class UserAdapterUTest {
    @Test
    void userToModel_should_convert_User_into_UserModel() throws InvalidFieldException {
        // given
        final Date created = new Date();
        final Date lastConnection = new Date();
        User user = new User("1232132", "toto", "toto@test.com", created, lastConnection);
        MongoUser expectedMongoUser = new MongoUser("1232132", "toto", "toto@test.com", created, lastConnection);

        // when
        MongoUser mongoUser = UserAdapter.userToModel(user);

        // then
        assertThat(mongoUser).isEqualToComparingFieldByField(expectedMongoUser);
    }

    @Test
    void modelToUser_should_convert_UserModel_into_User() throws InvalidFieldException {
        // given
        final Date created = new Date();
        final Date lastConnection = new Date();
        MongoUser mongoUser = new MongoUser("1232132", "toto", "toto@test.com", created, lastConnection);
        User expectedUser = new User("1232132", "toto", "toto@test.com", created, lastConnection);

        // when
        User user = UserAdapter.modelToUser(mongoUser);

        // then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }
}