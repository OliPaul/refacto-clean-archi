package com.cantet.refacto.infrastructure.dao;

import com.cantet.refacto.domain.UserRepository;
import com.cantet.refacto.domain.model.InvalidFieldException;
import com.cantet.refacto.domain.model.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoUserRepository implements UserRepository {

    private final MongoTemplate mongoTemplate;

    public MongoUserRepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public User add(User user) throws InvalidFieldException {
        final MongoUser mongoUser = UserAdapter.convertToUserModel(user);
        final MongoUser savedMongoUser = mongoTemplate.save(mongoUser);
        return UserAdapter.convertToUser(savedMongoUser);
    }

    public List<MongoUser> getAllUsers() {
        return mongoTemplate.findAll(MongoUser.class);
    }

    @Override
    public User getUserById(String user_id) throws InvalidFieldException {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user_id));
        final MongoUser mongoUser = mongoTemplate.findOne(query, MongoUser.class);

        return UserAdapter.convertToUser(mongoUser);
    }

    @Override
    public void updateUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user_id").is(user.getUserId()));
        Update update = new Update();
        update.set("name", user.getName());
        update.set("email", user.getEmail());
        update.set("last_connection", user.getLastConnection());
        mongoTemplate.findAndModify(query, update, MongoUser.class);
    }

}
