package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.infrastructure.model.UserModel;

public interface UserService {
    Float computeInterest(UserModel userModel);
}
