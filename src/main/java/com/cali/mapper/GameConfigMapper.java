package com.cali.mapper;

import com.cali.config.*;
import com.cali.dto.GameConfigDTO;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameConfigMapper {

    public GameConfig toGameConfig(GameConfigDTO dto) {
        GameConfig domain = GameConfig.getInstance();
        domain.rows = dto.rows;
        domain.columns = dto.columns;

        domain.symbols = dto.symbols.entrySet().stream()
                .map(e -> SymbolMapper.toSymbol(e.getKey(), e.getValue()))
                .collect(Collectors.toMap(Symbol::getSymbol, Function.identity()));

        domain.standardSymbols = domain.symbols.entrySet().stream()
                .filter(e -> e.getValue().getSymbolType() == SymbolType.STANDARD)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        domain.bonusSymbols = domain.symbols.entrySet().stream()
                .filter(e -> e.getValue().getSymbolType() == SymbolType.BONUS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        domain.repeatingCombinations = dto.win_combinations.values().stream()
                .filter(winCombinationDTO -> "same_symbols".equals(winCombinationDTO.group))
                .map(WinCombinationMapper::toRepeatingCombination)
                .collect(Collectors.toMap(RepeatingCombination::getTimes, Function.identity()));

        domain.linearCombinationMap = dto.win_combinations.values().stream()
                .filter(winCombinationDTO -> !"same_symbols".equals(winCombinationDTO.group))
                .map(WinCombinationMapper::toLinearCombination)
                .collect(Collectors.toMap(LinearCombination::getCombination, Function.identity()));

        domain.probabilities = ProbabilityMapper.toProbabilities(dto.probabilities);

        return domain;
    }
}
