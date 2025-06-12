package com.cali.config;

import java.util.function.Function;

public class Symbol {

    private final String symbol;
    private final RewardConfig rewardConfig;
    private final SymbolType symbolType;


    public Symbol(String symbol, String type, RewardConfig rewardConfig) {
        this.symbol = symbol;
        this.rewardConfig = rewardConfig;
        this.symbolType = SymbolType.toSymbolType(type);
    }

    public String getSymbol() {
        return symbol;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    public Function<Double, Double> getRewardCalculator() {
        return rewardConfig.rewardCalculator();
    }
}
