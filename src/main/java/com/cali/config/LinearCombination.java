package com.cali.config;

public class LinearCombination extends WinCombination {

    public enum Combination {
        HORIZONTALLY_LINEAR_SYMBOLS,
        VERTICALLY_LINEAR_SYMBOLS,
        LTR_DIAGONALLY_LINEAR_SYMBOLS,
        RTL_DIAGONALLY_LINEAR_SYMBOLS;

        public static Combination combinationOf(String type) {
            return valueOf(type.toUpperCase());
        }
    }

    private final Combination combination;
    public LinearCombination(String type, double factor) {
        super(factor);
        this.combination = Combination.combinationOf(type);
    }

    public Combination getCombination() {
        return combination;
    }
}
