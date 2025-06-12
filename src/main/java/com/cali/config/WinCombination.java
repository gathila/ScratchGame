package com.cali.config;

import java.util.function.Function;

public class WinCombination {

    protected String combinationName;
    protected double factor;

    public WinCombination(String combinationName, double factor) {
        this.combinationName = combinationName;
        this.factor = factor;
    }

    public String getCombinationName() {
        return combinationName;
    }

    public double getFactor() {
        return factor;
    }

    public Function<Double, Double> getRewardFunction() {
        return v -> Operation.MULTIPLY.apply(v, factor);
    }
}
