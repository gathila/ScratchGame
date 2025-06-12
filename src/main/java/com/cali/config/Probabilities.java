package com.cali.config;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

public class Probabilities {

    //probabilities of each symbol in given row and column
    private final Map<Pair<Integer, Integer>, StandardSymbolProbability> standardSymbolsProbabilities;
    private final Map<String, Integer> bonusSymbolsProbabilities;

    public Probabilities(Map<Pair<Integer, Integer>, StandardSymbolProbability> standardSymbolsProbabilities, Map<String, Integer> bonusSymbolsProbabilities) {
        this.standardSymbolsProbabilities = standardSymbolsProbabilities;
        this.bonusSymbolsProbabilities = bonusSymbolsProbabilities;
    }

    public Map<Pair<Integer, Integer>, StandardSymbolProbability> getStandardSymbolsProbabilities() {
        return standardSymbolsProbabilities;
    }

    public Map<String, Integer> getBonusSymbolsProbabilities() {
        return bonusSymbolsProbabilities;
    }
}
