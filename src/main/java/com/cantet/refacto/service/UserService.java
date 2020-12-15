package com.cantet.refacto.service;

import com.cantet.refacto.dao.MovementDAO;
import com.cantet.refacto.model.MovementModel;
import com.cantet.refacto.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final MovementDAO movementDAO;

    public UserService(MovementDAO movementDAO) {
        this.movementDAO = movementDAO;
    }

    public Float computeInterest(UserModel userModel) {
        final List<MovementModel> allUserMovements = movementDAO.getCredits(userModel);

        final Float interests = allUserMovements.stream()
                .map(movementModel -> movementModel.getCredit() * 1.2f)
                .reduce(0f, Float::sum);

        return interests;
    }
}
