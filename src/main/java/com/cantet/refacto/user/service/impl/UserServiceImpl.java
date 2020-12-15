package com.cantet.refacto.user.service.impl;

import com.cantet.refacto.user.dao.MovementDAO;
import com.cantet.refacto.user.model.MovementModel;
import com.cantet.refacto.user.model.UserModel;
import com.cantet.refacto.user.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final float PERCENTAGE_INTEREST = 1.2f;

    private final MovementDAO MovementDAO;

    public UserServiceImpl(MovementDAO MovementDAO) {
        this.MovementDAO = MovementDAO;
    }

    @Override
    public Float computeInterest(UserModel userModel) {
        final List<MovementModel> allUserMovements = MovementDAO.getCredits(userModel);

        final Float interests = allUserMovements.stream()
                .map(movementModel -> movementModel.getCredit() * PERCENTAGE_INTEREST)
                .reduce(0f, Float::sum);

        return interests;
    }
}
