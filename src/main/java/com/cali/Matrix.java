package com.cali;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Matrix {

    private final Random randomGen = new Random();
    double bonusChance = 0.1; //Change this to dynamic

    public String[][] randomMetrixOf(int size) {
        
        WeightedRandomLetter wl = new WeightedRandomLetter(letters(), symbols());
        String [][] metrix = new String[size][size];
        
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {

                if (randomGen.nextDouble() < bonusChance) {
                    metrix[i][j] = wl.getWeightRandomSymbol();
                } else {
                    metrix[i][j] = wl.getWeightedRandomLetter();
                }
            }
        }
        
        return metrix;
    }



    private Map<String, Integer> letters() {
        Map<String, Integer> letterWeights = new LinkedHashMap<>();

        letterWeights.put("A", 1);
        letterWeights.put("B", 2);
        letterWeights.put("C", 3);
        letterWeights.put("D", 4);
        letterWeights.put("E", 5);
        letterWeights.put("F", 6);

        return letterWeights;
    }

    private Map<String, Integer> symbols() {
        Map<String, Integer> symbolWeights = new LinkedHashMap<>();

        symbolWeights.put("10x", 1);
        symbolWeights.put("5x", 2);
        symbolWeights.put("+1000", 3);
        symbolWeights.put("+500", 4);
        symbolWeights.put("MISS", 5);

        return symbolWeights;
    }
    
}
