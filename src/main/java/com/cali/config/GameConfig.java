package com.cali.config;

import java.util.*;

public class GameConfig {

    private static final GameConfig instance = new GameConfig();
    public static GameConfig getInstance() {
        return instance;
    }

    private GameConfig() {}
    public int rows;
    public int columns;
    public Set<String> standardSymbols ;
    public Set<String> bonusSymbols;
    public Map<String, Symbol> symbols;

    //Grouped by number of repeats
    public Map<Integer, RepeatingCombination> repeatingCombinations;
    public Map<LinearCombination.LinearCombinationType, LinearCombination> linearCombinationMap;
    public Probabilities probabilities;

    public void populateConfiguration() {

    }

}
