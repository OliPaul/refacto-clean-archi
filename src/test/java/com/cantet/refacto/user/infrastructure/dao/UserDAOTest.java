package com.cantet.refacto.user.infrastructure.dao;

import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.service.InvalidFieldException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {

    private UserDAO userDAO;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO(mongoTemplate);
    }

    @Nested
    class AddUserShould {

        private User user;
        private UserModel userModel;

        @BeforeEach
        void setUp() throws InvalidFieldException {
            final String userId = "23424524523412";
            final String name = "Test";
            final String email = "test@test.fr";
            final Date created = new Date();
            final Date lastConnection = new Date();

            user = new User(userId, name, email, created, lastConnection);

            userModel = new UserModel(userId, name, email, created, lastConnection);

            when(mongoTemplate.save(any())).thenReturn(userModel);
        }

        @Test
        public void return_added_user() throws InvalidFieldException {
            // when
            final User result = userDAO.addUser(user);

            // then
            assertThat(result).isEqualToComparingFieldByField(user);
        }

        @Test
        public void call_mongoSave() throws InvalidFieldException {
            // when
            userDAO.addUser(user);

            // then
            ArgumentCaptor<UserModel> userModelArgumentCaptor = ArgumentCaptor.forClass(UserModel.class);
            verify(mongoTemplate).save(userModelArgumentCaptor.capture());
            assertThat(userModelArgumentCaptor.getValue()).isEqualToComparingFieldByField(userModel);
        }
    }

    @Test
    public void getAllUsers_should_return_all_users() throws InvalidFieldException {
        // given
        final Date created = new Date();
        final Date lastConnection = new Date();
        final UserModel user1 = new UserModel("", "Test1", "test1@test.fr", created, lastConnection);
        final UserModel user2 = new UserModel("", "Test2", "test2@test.fr", created, lastConnection);
        when(mongoTemplate.findAll(UserModel.class)).thenReturn(asList(user1, user2));

        final User expectedUser1 = new User("", "Test1", "test1@test.fr", created, lastConnection);
        final User expectedUser2 = new User("", "Test2", "test2@test.fr", created, lastConnection);

        // when
        final List<User> users = userDAO.getAllUsers();

        // then
        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualToComparingFieldByField(expectedUser1);
        assertThat(users.get(1)).isEqualToComparingFieldByField(expectedUser2);
    }

    @Test
    public void getUserById_should_return_matching_user() throws InvalidFieldException {
        // given
        final String userId = "34234234234";

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        final User expectedUser = new User(userId, "Test", "test@test.fr", new Date(), new Date());
        final UserModel userModel = new UserModel(userId, "Test", "test@test.fr", new Date(), new Date());
        when(mongoTemplate.findOne(query, UserModel.class)).thenReturn(userModel);

        // when
        final User user = userDAO.getUserById(userId);

        // then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void update_should_call_findAndModify() throws InvalidFieldException {
        // given
        final String userId = "34234234234";
        final String name = "Test";
        final String email = "test@test.fr";
        final Date lastConnection = new Date();

        final User user = new User(userId, name, email, new Date(), lastConnection);

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        Update update = new Update();
        update.set("name", name);
        update.set("email", email);
        update.set("last_connection", lastConnection);

        // when
        userDAO.updateUser(user);

        // then
        verify(mongoTemplate).findAndModify(query, update, UserModel.class);
    }
}
