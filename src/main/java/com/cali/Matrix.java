package com.cali;

import com.cali.config.Probabilities;
import com.cali.config.StandardSymbolProbability;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.Random;

public class Matrix {

    private final Random randomGen = new Random();
    private final double bonusChance = 0.1; //Change this to dynamic
    private final Probabilities probabilities;

    public Matrix(Probabilities probabilities) {
        this.probabilities = probabilities;
    }

    public String[][] randomMatrixOf(int rows, int columns) {
        
        WeightedRandomSymbolGenerator wl = new WeightedRandomSymbolGenerator();
        String [][] matrix = new String[rows][columns];
        
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {

                Map<String, Integer> symbolProbabilities;
                if (randomGen.nextDouble() < bonusChance) {
                    symbolProbabilities = probabilities.getBonusSymbolsProbabilities();
                } else {
                    StandardSymbolProbability standardSymbolProbability = probabilities.getStandardSymbolsProbabilities().get(Pair.of(i, j));
                    symbolProbabilities = standardSymbolProbability.getSymbols();
                }

                matrix[i][j] = wl.getWeightedRandomSymbol(symbolProbabilities);
            }
        }
        
        return matrix;
    }
    
}
