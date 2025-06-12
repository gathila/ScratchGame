package com.cali.config;

import java.util.function.Function;

public class WinCombination {

    protected String combinationName;
    protected double factor;

    public WinCombination(double factor) {
        this.factor = factor;
    }
    public double getFactor() {
        return factor;
    }

    public Function<Double, Double> getRewardFunction() {
        return v -> Operation.MULTIPLY.apply(v, factor);
    }
}
