package com.cali.config;

public class LinearCombination extends WinCombination {

    public enum LinearCombinationType {
        HORIZONTALLY_LINEAR_SYMBOLS,
        VERTICALLY_LINEAR_SYMBOLS,
        LTR_DIAGONALLY_LINEAR_SYMBOLS,
        RTL_DIAGONALLY_LINEAR_SYMBOLS;

        public static LinearCombinationType combinationOf(String type) {
            return valueOf(type.toUpperCase());
        }
    }

    private final LinearCombinationType linearCombinationType;
    public LinearCombination(String combinationName, String type, double factor) {
        super(combinationName, factor);
        this.linearCombinationType = LinearCombinationType.combinationOf(type);
    }

    public LinearCombinationType getCombination() {
        return linearCombinationType;
    }
}
