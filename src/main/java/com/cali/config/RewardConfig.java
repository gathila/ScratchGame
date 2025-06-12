package com.cali.config;

import java.util.function.Function;

public class RewardConfig {

    private final Operation operation;
    private final double factor;

    public RewardConfig(String action, double factor) {
        this.operation = Operation.toOperation(action);
        this.factor = factor;
    }

    public Function<Double, Double> rewardCalculator() {
        return v -> operation.apply(v, factor);
    }

}
