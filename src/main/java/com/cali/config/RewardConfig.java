package com.cali.config;

import java.security.InvalidParameterException;
import java.util.function.Function;

public class RewardConfig {

    private final Operation operation;
    private final double factor;

    public RewardConfig(Double multiplier, Double extra) {

        if (multiplier != null) {
            this.operation = Operation.toOperation("reward_multiplier");
            this.factor = multiplier;
        }

        else if (extra != null) {
            this.operation = Operation.toOperation("extra");
            this.factor = extra;
        }

        else
            throw new InvalidParameterException("Either multiplier or extra is required");
    }

    public Function<Double, Double> rewardCalculator() {
        return v -> operation.apply(v, factor);
    }

}
