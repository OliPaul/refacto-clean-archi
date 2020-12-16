package com.cantet.refacto.user.use_case;

import com.cantet.refacto.user.domain.User;

public interface ComputeInterests {
    Float execute(User user);
}
