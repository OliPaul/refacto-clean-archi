package com.cantet.refacto.user.infrastructure.dao;

import com.cantet.refacto.user.infrastructure.model.MovementModel;
import com.cantet.refacto.user.infrastructure.model.UserModel;

import java.util.List;

public interface MovementDAO {
    List<MovementModel> getCredits(UserModel userModel);
}
