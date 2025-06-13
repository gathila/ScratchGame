package com.cali.config;


public class Symbol {

    private final String symbol;
    private final SymbolType symbolType;
    private final ImpactType impact;
    private final Double rewardMultiplier;
    private final Double extra;


    public Symbol(String symbol, String type, Double rewardMultiplier, Double extra, ImpactType impact) {
        this.symbol = symbol;
        this.symbolType = SymbolType.toSymbolType(type);
        this.impact = impact;
        this.rewardMultiplier = rewardMultiplier;
        this.extra = extra;
    }

    public String getSymbol() {
        return symbol;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    public Double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public double applyImpact(double currentReward) {

         return switch (impact) {
            case MULTIPLY_REWARD -> currentReward * rewardMultiplier;
             case EXTRA_BONUS       -> currentReward + extra;
            case MISS            -> 0;
            default              -> currentReward;
        };
    }
}
