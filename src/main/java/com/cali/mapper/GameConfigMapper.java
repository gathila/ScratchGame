package com.cali.mapper;

import com.cali.config.*;
import com.cali.dto.GameConfigDTO;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameConfigMapper {

    public static GameConfig toGameConfig(GameConfigDTO dto) {
        GameConfig domain = GameConfig.getInstance();
        domain.setRows(dto.rows);
        domain.setColumns(dto.columns);

        domain.setSymbols(dto.symbols.entrySet().stream()
                .map(e -> SymbolMapper.toSymbol(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(Symbol::getSymbol, Function.identity())));

        domain.setStandardSymbols(domain.getSymbols().entrySet().stream()
                .filter(e -> e.getValue().getSymbolType() == SymbolType.STANDARD)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet()));

        domain.setBonusSymbols(domain.getSymbols().entrySet().stream()
                .filter(e -> e.getValue().getSymbolType() == SymbolType.BONUS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet()));

        domain.setRepeatingCombinations(dto.win_combinations.entrySet().stream()
                .filter(entry -> "same_symbols".equals(entry.getValue().group))
                .map(entry -> WinCombinationMapper.toRepeatingCombination(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(RepeatingCombination::getTimes, Function.identity())));

        domain.setLinearCombinationMap(dto.win_combinations.entrySet().stream()
                .filter(entry -> !"same_symbols".equals(entry.getValue().group))
                .map(entry -> WinCombinationMapper.toLinearCombination(entry.getKey(), entry.getValue()))
                .collect(Collectors.toMap(LinearCombination::getCombination, Function.identity())));

        domain.setProbabilities(ProbabilityMapper.toProbabilities(dto.probabilities));

        return domain;
    }
}
