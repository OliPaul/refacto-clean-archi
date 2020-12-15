package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.service.User;

public interface ComputeInterests {
    Float execute(User user);
}
