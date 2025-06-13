package com.cali.config;

import java.util.*;

public class GameConfig {

    private static final GameConfig instance = new GameConfig();
    public static GameConfig getInstance() {
        return instance;
    }

    private GameConfig() {}
    private int rows;
    private int columns;
    private Set<String> standardSymbols ;
    private Set<String> bonusSymbols;
    private Map<String, Symbol> symbols;

    //Grouped by number of repeats
    private Map<Integer, RepeatingCombination> repeatingCombinations;
    private Map<LinearCombination.LinearCombinationType, LinearCombination> linearCombinationMap;
    private Probabilities probabilities;


    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public Set<String> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(Set<String> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public Set<String> getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(Set<String> bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Symbol> symbols) {
        this.symbols = symbols;
    }

    public Map<Integer, RepeatingCombination> getRepeatingCombinations() {
        return repeatingCombinations;
    }

    public void setRepeatingCombinations(Map<Integer, RepeatingCombination> repeatingCombinations) {
        this.repeatingCombinations = repeatingCombinations;
    }

    public Map<LinearCombination.LinearCombinationType, LinearCombination> getLinearCombinationMap() {
        return linearCombinationMap;
    }

    public void setLinearCombinationMap(Map<LinearCombination.LinearCombinationType, LinearCombination> linearCombinationMap) {
        this.linearCombinationMap = linearCombinationMap;
    }

    public Probabilities getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Probabilities probabilities) {
        this.probabilities = probabilities;
    }
}
