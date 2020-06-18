package com.cantet.refacto.user.infrastructure.dao;

import com.cantet.refacto.user.domain.UserRepository;
import com.cantet.refacto.user.domain.model.User;
import com.cantet.refacto.user.domain.model.InvalidFieldException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDAO implements UserRepository {

    private final MongoTemplate mongoTemplate;

    public UserDAO(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User addUser(User user) throws InvalidFieldException {
        final UserModel userModel = UserAdapter.userToModel(user);
        final UserModel savedUser = mongoTemplate.save(userModel);
        return UserAdapter.modelToUser(savedUser);
    }

    @Override
    public List<User> getAllUsers() throws InvalidFieldException {
        final List<UserModel> userModels = mongoTemplate.findAll(UserModel.class);
        final List<User> users = new ArrayList<>();
        for (UserModel userModel: userModels
             ) {
            final User user = UserAdapter.modelToUser(userModel);
            users.add(user);
        }

        return users;
    }

    @Override
    public User getUserById(String userId) throws InvalidFieldException {
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
