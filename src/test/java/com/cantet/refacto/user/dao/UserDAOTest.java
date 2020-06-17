package com.cantet.refacto.user.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

        @Test
        public void return_added_user() {
            // given
            UserModel expectedUser = new UserModel("23424524523412", "Test", "test@test.fr", new Date(), new Date());

            when(mongoTemplate.save(expectedUser)).thenReturn(expectedUser);

            // when
            final UserModel userModel = userDAO.addUser(expectedUser);

            // then
            assertThat(userModel).isEqualTo(expectedUser);
        }

        @Test
        public void call_mongoSave() {
            // given
            UserModel expectedUser = new UserModel("23424524523412", "Test", "test@test.fr", new Date(), new Date());
            when(mongoTemplate.save(expectedUser)).thenReturn(expectedUser);

            // when
            final UserModel userModel = userDAO.addUser(expectedUser);

            // then
            verify(mongoTemplate).save(userModel);
        }
    }

    @Test
    public void getAllUsers_should_return_all_users() {
        // given
        final UserModel user1 = new UserModel("", "Test1", "test1@test.fr", new Date(), new Date());
        final UserModel user2 = new UserModel("", "Test2", "test2@test.fr", new Date(), new Date());
        final List<UserModel> userModels = asList(user1, user2);
        when(mongoTemplate.findAll(UserModel.class)).thenReturn(userModels);

        // when
        final List<UserModel> users = userDAO.getAllUsers();

        // then
        assertThat(users).hasSize(2);
        assertThat(users).containsExactly(user1, user2);
    }

    @Test
    public void getUserById_should_return_matching_user() {
        // given
        final String userId = "34234234234";

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        final UserModel expectedUser = new UserModel(userId, "Test", "test@test.fr", new Date(), new Date());
        when(mongoTemplate.findOne(query, UserModel.class)).thenReturn(expectedUser);

        // when
        final UserModel user = userDAO.getUserById(userId);

        // then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    void update_should_call_findAndModify() {
        // given
        final String userId = "34234234234";
        final String name = "Test";
        final String email = "test@test.fr";
        final Date lastConnection = new Date();
        final UserModel userModel = new UserModel(userId, name, email, new Date(), lastConnection);

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        Update update = new Update();
        update.set("name", name);
        update.set("email", email);
        update.set("last_connection", lastConnection);

        // when
        userDAO.updateUser(userModel);

        // then
        verify(mongoTemplate).findAndModify(query, update, UserModel.class);
    }
}
