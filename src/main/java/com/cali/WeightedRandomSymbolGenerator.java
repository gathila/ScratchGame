package com.cali;

import java.util.Map;
import java.util.Random;

public class WeightedRandomSymbolGenerator {

    private final Random randomGen = new Random();


    public String getWeightedRandomSymbol(Map<String, Integer> letterWeights) {
        int totalWeight = letterWeights.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
        return getRandom(letterWeights, totalWeight);
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
