package com.cali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GameResults {

    private String [][] matrix;
    private double reward = 0.0;

    @JsonProperty("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;

    @JsonProperty("applied_bonus_symbol")
    private String appliedBonusSymbol;

    public GameResults() {
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
