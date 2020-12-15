package com.cantet.refacto.user.infrastructure.dao;

import com.cantet.refacto.user.domain.service.Movement;
import com.cantet.refacto.user.domain.service.User;
import com.cantet.refacto.user.infrastructure.dao.impl.MongoMovementDAO;
import com.cantet.refacto.user.infrastructure.dao.impl.MongoMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MongoMovementDAOTest {

    private MongoMovementDAO mongoMovementDAOImpl;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        mongoMovementDAOImpl = new MongoMovementDAO(mongoTemplate);
    }

    @Test
    public void getCredits_should_return_all_movement() {
        // given
        final Integer userId = 42;
        final MongoMovement mongoMovement1 = new MongoMovement(1, userId, 1f);
        final MongoMovement mongoMovement2 = new MongoMovement(2, userId, 2f);
        final List<MongoMovement> mongoMovements = asList(
                mongoMovement1,
                mongoMovement2,
                new MongoMovement(3, 999, 2f));
        when(mongoTemplate.findAll(MongoMovement.class)).thenReturn(mongoMovements);

        User user = new User(userId);

        // when
        final List<Movement> movements = mongoMovementDAOImpl.getCredits(user);

        // then
        assertThat(movements).hasSize(2);

        final Movement movement1 = new Movement(1f);
        final Movement movement2 = new Movement(2f);
        assertThat(movements).usingRecursiveFieldByFieldElementComparator().containsExactly(movement1, movement2);

    }
}
