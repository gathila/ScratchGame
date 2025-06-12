package com.cali.config;

import java.util.function.Function;

public class SymbolConfig {

    private final String symbol;
    private final RewardConfig rewardConfig;


    public SymbolConfig(String symbol, String action, double factor) {
        this.symbol = symbol;
        this.rewardConfig = new RewardConfig(action, factor);
    }

    public String getSymbol() {
        return symbol;
    }

    public Function<Double, Double> getRewardCalculator() {
        return rewardConfig.rewardCalculator();
    }
}
