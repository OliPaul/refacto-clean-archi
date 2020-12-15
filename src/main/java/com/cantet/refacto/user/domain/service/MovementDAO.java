package com.cantet.refacto.user.domain.service;

import java.util.List;

public interface MovementDAO {
    List<Movement> getCredits(User user);
}
