package com.cali.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Results {

    private String [][] matrix;
    private double reward = 0.0;
    private Map<String, List<String>> appliedWinningCombinations;
    private String appliedBonusSymbol;

    public Results() {
    }

    public String[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(String[][] matrix) {
        this.matrix = matrix;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public Map<String, List<String>> getAppliedWinningCombinations() {
        return appliedWinningCombinations;
    }

    public void setAppliedWinningCombinations(Map<String, List<String>> appliedWinningCombinations) {
        this.appliedWinningCombinations = appliedWinningCombinations;
    }

    public String getAppliedBonusSymbol() {
        return appliedBonusSymbol;
    }

    public void setAppliedBonusSymbol(String appliedBonusSymbol) {
        this.appliedBonusSymbol = appliedBonusSymbol;
    }


    @Override
    public String toString() {
        return "Results{" +
                "matrix: " + Arrays.deepToString(matrix) +
                ", \nreward: " + reward +
                ", \nappliedWinningCombinations=" + appliedWinningCombinations +
                ", \nappliedBonusSymbol='" + appliedBonusSymbol + '\'' +
                '}';
    }
}
