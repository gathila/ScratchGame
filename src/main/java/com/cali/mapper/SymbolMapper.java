package com.cali.mapper;

import com.cali.config.ImpactType;
import com.cali.config.Symbol;
import com.cali.dto.SymbolDefinitionDTO;

public class SymbolMapper {


    public static Symbol toSymbol(String symbol, SymbolDefinitionDTO dto) {

        return new Symbol(symbol, dto.type, dto.reward_multiplier, dto.extra,
                ImpactType.toImpactType(dto.impact == null ? "multiply_reward" : dto.impact));
    }
}
