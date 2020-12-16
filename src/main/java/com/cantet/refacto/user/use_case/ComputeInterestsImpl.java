package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.InterestHelper;
import com.cantet.refacto.user.domain.Movement;
import com.cantet.refacto.user.domain.MovementDAO;
import com.cantet.refacto.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComputeInterestsImpl implements ComputeInterests {

    private final MovementDAO movementDAO;

    public ComputeInterestsImpl(MovementDAO MovementDAO) {
        this.movementDAO = MovementDAO;
    }

    @Override
    public Float execute(User user) {
        final List<Movement> allUserMovements = movementDAO.getCredits(user);

        return InterestHelper.computeInterest(allUserMovements);
    }
}
