package com.cali.config;

import java.util.*;

public class GameConfig {

    private static GameConfig instance = new GameConfig();
    public static GameConfig getInstance() {
        return instance;
    }

    private GameConfig() {}
    public int rows;
    public int columns;

    public Set<String> standardSymbols = new HashSet<>();
    public Set<String> bonusSymbols = new HashSet<>();
    public Map<String, Symbol> symbols = new HashMap<>();
    public Map<Integer, RepeatingCombination> repeatingCombinations = new HashMap<>();
    public Map<LinearCombination.Combination, LinearCombination> lenierCombinationMap = new HashMap<>();


    public void populateConfiguration() {

    }

}
