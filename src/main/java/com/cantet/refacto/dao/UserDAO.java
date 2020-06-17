package com.cantet.refacto.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDAO {

    private final MongoTemplate mongoTemplate;

    public UserDAO(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public UserModel addUser(UserModel user) {
        return mongoTemplate.save(user);
    }

    public List<UserModel> getAllUsers() {
        return mongoTemplate.findAll(UserModel.class);
    }

    public UserModel getUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(userId));
        return mongoTemplate.findOne(query, UserModel.class);
    }

    public void updateUser(UserModel user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUserId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("email", user.getEmail());
        update.set("last_connection", user.getLastConnection());
        mongoTemplate.findAndModify(query, update, UserModel.class);
    }
}
