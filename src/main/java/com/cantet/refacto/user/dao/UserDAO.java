package com.cantet.refacto.user.dao;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserDAO implements UserRepository {

    private final MongoTemplate mongoTemplate;

    public UserDAO(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User addUser(User user) {
        final UserModel userModel = UserAdapter.userToModel(user);
        final UserModel savedUser = mongoTemplate.save(userModel);
        return UserAdapter.modelToUser(savedUser);
    }

    @Override
    public List<User> getAllUsers() {
        final List<UserModel> users = mongoTemplate.findAll(UserModel.class);
        return users.stream()
                .map(UserAdapter::modelToUser)
                .collect(toList());
    }

    @Override
    public User getUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        final UserModel userModel = mongoTemplate.findOne(query, UserModel.class);
        return UserAdapter.modelToUser(userModel);
    }

    @Override
    public void updateUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUserId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("email", user.getEmail());
        update.set("last_connection", user.getLastConnection());
        mongoTemplate.findAndModify(query, update, UserModel.class);
    }
}
