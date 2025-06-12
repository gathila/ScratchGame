package com.cali;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class WeightedRandomLetter {

    private Map<String, Integer> letterWeights = new LinkedHashMap<>();
    private Map<String, Integer> symbolWeights = new LinkedHashMap<>();
    private final Random randomGen = new Random();
    int totalLetterWeight;
    int totalSymbolWeight;

    public WeightedRandomLetter(Map<String, Integer> letterWeights,
                                Map<String, Integer> symbolWeights) {
        this.letterWeights = new LinkedHashMap<>(letterWeights);
        this.totalLetterWeight = this.letterWeights.values().stream()
                .mapToInt(Integer::intValue)
                .sum();

        this.symbolWeights = new LinkedHashMap<>(symbolWeights);
        this.totalSymbolWeight = this.symbolWeights.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void initialise(Map<String, Integer> w) {
        letterWeights.put("A", 1);
        letterWeights.put("B", 2);
        letterWeights.put("C", 3);
        letterWeights.put("D", 4);
        letterWeights.put("E", 5);
        letterWeights.put("F", 6);


    }


    public String getWeightedRandomLetter() {
        return getRandom(letterWeights, totalLetterWeight);
    }

    public String getWeightRandomSymbol() {
        return getRandom(symbolWeights, totalSymbolWeight);
    }

    private String getRandom(Map<String, Integer> weights, int totalWeight) {
        int random = randomGen.nextInt(totalWeight) + 1;

        int cumulativeWeight = 0;
        for (Map.Entry<String, Integer> entry : weights.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (random <= cumulativeWeight) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Should not reach here");
    }

}
