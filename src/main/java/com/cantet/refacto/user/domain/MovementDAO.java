package com.cantet.refacto.user.domain;

import java.util.List;

public interface MovementDAO {
    List<Movement> getCredits(User user);
}
