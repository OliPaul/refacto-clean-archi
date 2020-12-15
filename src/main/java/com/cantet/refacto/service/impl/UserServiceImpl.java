package com.cantet.refacto.service.impl;

import com.cantet.refacto.dao.MovementDAO;
import com.cantet.refacto.model.MovementModel;
import com.cantet.refacto.model.UserModel;
import com.cantet.refacto.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final MovementDAO MovementDAO;

    public UserServiceImpl(MovementDAO MovementDAO) {
        this.MovementDAO = MovementDAO;
    }

    @Override
    public Float computeInterest(UserModel userModel) {
        final List<MovementModel> allUserMovements = MovementDAO.getCredits(userModel);

        final Float interests = allUserMovements.stream()
                .map(movementModel -> movementModel.getCredit() * 1.2f)
                .reduce(0f, Float::sum);

        return interests;
    }
}
