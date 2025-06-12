package com.cali.config;

public class RepeatingCombination extends WinCombination {

    protected int times;


    public RepeatingCombination(String combinationName, double factor, int times) {
        super(combinationName, factor);
        this.times = times;
    }

    public int getTimes() {
        return times;
    }
}
