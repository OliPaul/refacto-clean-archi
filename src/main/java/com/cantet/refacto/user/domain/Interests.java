package com.cantet.refacto.user.domain;

import java.util.List;

public class Interests {

    private static final float PERCENTAGE_INTEREST = 1.2f;

    private final List<Movement> allUserMovements;

    public Interests(List<Movement> allUserMovements) {
        this.allUserMovements = allUserMovements;
    }

    public Float compute() {
        final Float interests = allUserMovements.stream()
                .map(movement -> movement.getCredit() * PERCENTAGE_INTEREST)
                .reduce(0f, Float::sum);

        return interests;
    }
}
