package com.cantet.refacto.infrastructure.dao;

import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MongoUserRepositoryTest {

    private MongoUserRepository mongoUserRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoUserRepository = new MongoUserRepository(mongoTemplate);
    }

    @Test
    public void addUser_should_return_added_user() throws InvalidFieldException {
        // given
        User expectedUser = new User("23424524523412", "Test", "test@test.fr", new Date(), new Date());
        final MongoUser mongoUser = UserAdapter.convertToUserModel(expectedUser);
        when(mongoTemplate.save(any())).thenReturn(mongoUser);

        // when
        final User user = mongoUserRepository.add(expectedUser);

        // then
        assertThat(user).isEqualToComparingFieldByField(expectedUser);
    }

    @Test
    public void getAllUsers_should_return_all_users() {
        // given
        final List<MongoUser> userModels = asList(new MongoUser("", "Test", "test@test.fr", new Date(), new Date()), new MongoUser("", "Test", "test@test.fr", new Date(), new Date()));
        when(mongoTemplate.findAll(MongoUser.class)).thenReturn(userModels);

        // when
        final List<MongoUser> users = mongoUserRepository.getAllUsers();

        // then
        assertThat(users).hasSize(2);
    }

    @Test
    public void getUserById_should_return_matching_user() throws InvalidFieldException {
        // given
        final String userId = "34234234234";

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        final Date created = new Date();
        final Date lastConnection = new Date();
        final MongoUser mongoUser = new MongoUser(userId, "Test", "test@test.fr", created, lastConnection);
        when(mongoTemplate.findOne(query, MongoUser.class)).thenReturn(mongoUser);
        User expectedUser = new User(userId, "Test", "test@test.fr", created, lastConnection);

        // when
        final User user = mongoUserRepository.getUserById(userId);

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
        final User userModel = new User(userId, name, email, new Date(), lastConnection);

        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));

        Update update = new Update();
        update.set("name", name);
        update.set("email", email);
        update.set("last_connection", lastConnection);

        // when
        mongoUserRepository.updateUser(userModel);

        // then
        verify(mongoTemplate).findAndModify(query, update, MongoUser.class);
    }
}
