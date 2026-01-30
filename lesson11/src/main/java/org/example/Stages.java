package org.example;

import java.util.Random;

public enum Stages {

    PREPARE(1, 3),
    BAKE(3, 6),
    DELIVER(2, 5);

    private final int minimumDuration;
    private final int maximumDuration;

    Stages (
            int minimumDuration,
            int maximumDuration
    ) {
        this.minimumDuration = minimumDuration;
        this.maximumDuration = maximumDuration;
    }

    public int getRandomDuration() {
        Random random = new Random();
        return random.nextInt(maximumDuration - minimumDuration + 1) + minimumDuration;
    }
}