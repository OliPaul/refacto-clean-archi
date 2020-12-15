package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.service.InterestHelper;
import com.cantet.refacto.user.domain.service.Movement;
import com.cantet.refacto.user.infrastructure.dao.MovementDAO;
import com.cantet.refacto.user.infrastructure.model.MovementModel;
import com.cantet.refacto.user.infrastructure.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final MovementDAO movementDAO;

    public UserServiceImpl(MovementDAO MovementDAO) {
        this.movementDAO = MovementDAO;
    }

    @Override
    public Float computeInterest(UserModel userModel) {
        final List<MovementModel> allUserMovements = movementDAO.getCredits(userModel);

        final List<Movement> movements = allUserMovements
                .stream()
                .map(movementModel -> new Movement(movementModel.getCredit()))
                .collect(Collectors.toList());

        return InterestHelper.computeInterest(movements);
    }
}
