package com.cali.config;

import java.util.function.DoubleBinaryOperator;

public enum Operation {
    MULTIPLY((e1, e2) -> e1*e2),
    ADD(Double::sum);

    private final DoubleBinaryOperator function;

    Operation(DoubleBinaryOperator function) {
        this.function = function;
    }

    public double apply(double v1, double v2) {
        return function.applyAsDouble(v1, v2);
    }

    public static Operation toOperation(String action) {
        if ("reward_multiplier".equals(action)) {
            return MULTIPLY;
        }

        if ("extra".equals(action)) {
            return ADD;
        }

        throw new IllegalArgumentException();
    }
}
