package com.cantet.refacto.user.dao;

import com.cantet.refacto.user.model.MovementModel;
import com.cantet.refacto.user.model.UserModel;

import java.util.List;

public interface MovementDAO {
    List<MovementModel> getCredits(UserModel userModel);
}
