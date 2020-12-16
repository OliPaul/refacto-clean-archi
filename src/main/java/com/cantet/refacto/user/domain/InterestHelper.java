package com.cantet.refacto.user.domain;

import java.util.List;

public class InterestHelper {

    private static final float PERCENTAGE_INTEREST = 1.2f;

    public static Float computeInterest(List<Movement> allUserMovements) {
        final Float interests = allUserMovements.stream()
                .map(movement -> movement.getCredit() * PERCENTAGE_INTEREST)
                .reduce(0f, Float::sum);

        return interests;
    }
}
