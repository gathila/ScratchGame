package com.cali.dto;


import java.util.Map;

public class GameConfigDTO {

    public int columns;
    public int rows;
    public Map<String, SymbolDefinitionDTO> symbols;
    public ProbabilitiesDTO probabilities;
    public Map<String, WinCombinationDTO> win_combinations;

}
