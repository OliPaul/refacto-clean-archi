package com.cantet.refacto.user.domain;

public class Movement {
    private final Float credit;

    public Movement(Float credit) {
        this.credit = credit;
    }

    public Float getCredit() {
        return credit;
    }
}
