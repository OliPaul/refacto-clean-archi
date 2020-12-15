package com.cantet.refacto.dao;

import com.cantet.refacto.model.MovementModel;
import com.cantet.refacto.model.UserModel;

import java.util.List;

public interface MovementDAO {
    List<MovementModel> getCredits(UserModel userModel);
}
