package com.cali.config;

import java.util.*;

public class Configurations {

    int rows;
    int columns;

    public Set<String> standardSymbols = new HashSet<>();
    public Set<String> bonusSymbols = new HashSet<>();
    public Map<String, SymbolConfig> symbolConfigs = new HashMap<>();
    public Map<Integer, RepeatingCombination> repeatingCombinations = new HashMap<>();
    public Map<String, LinearCombination> lenierCombinationMap = new HashMap<>();


    public void populateConfiguration() {

    }

}
