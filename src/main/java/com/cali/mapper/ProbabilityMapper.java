package com.cali.mapper;

import com.cali.config.Probabilities;
import com.cali.config.StandardSymbolProbability;
import com.cali.dto.ProbabilitiesDTO;
import com.cali.dto.StandardSymbolProbabilityDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProbabilityMapper {

    public static Probabilities toProbabilities(ProbabilitiesDTO dto) {
        Map<Pair<Integer, Integer>, StandardSymbolProbability> standardSymbolProbabilities = dto.standard_symbols.stream()
                .map(ProbabilityMapper::toStandardSymbolProbability)
                .collect(Collectors.toMap(e -> Pair.of(e.getRow(), e.getColumn()), Function.identity()));

        return new Probabilities(standardSymbolProbabilities, dto.bonus_symbols.symbols);
    }

    private static StandardSymbolProbability toStandardSymbolProbability(StandardSymbolProbabilityDTO dto) {
        return new StandardSymbolProbability(dto.row, dto.column, dto.symbols);
    }
}
