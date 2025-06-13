package com.cali.config;

public class WinCombination {

    protected String combinationName;
    protected double rewardMultiplier;

    public WinCombination(String combinationName, double rewardMultiplier) {
        this.combinationName = combinationName;
        this.rewardMultiplier = rewardMultiplier;
    }


    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public String getCombinationName() {
        return combinationName;
    }
}
