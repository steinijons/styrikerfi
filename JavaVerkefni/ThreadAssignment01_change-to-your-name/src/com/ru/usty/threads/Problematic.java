package com.ru.usty.threads;

import java.util.Random;

public class Problematic {

    public static final int PROBLEM_WIDTH = 640;

    private static Random random = new Random(1534748);
    public static Problem nextProblem() {
        return new Problem(new Tile(random.nextInt(PROBLEM_WIDTH), random.nextInt(PROBLEM_WIDTH)),
                new Tile(random.nextInt(PROBLEM_WIDTH), random.nextInt(PROBLEM_WIDTH)));
    }
}
