package com.cali.config;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

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
    private final List<CoveredArea> coveredAreas;
    public LinearCombination(String combinationName, String type, double factor, List<CoveredArea> coveredAreas) {
        super(combinationName, factor);
        this.linearCombinationType = LinearCombinationType.combinationOf(type);
        this.coveredAreas = coveredAreas;
    }

    public LinearCombinationType getCombination() {
        return linearCombinationType;
    }

    public List<CoveredArea> getCoveredAreas() {
        return coveredAreas;
    }
}
