package com.cali.mapper;

import com.cali.config.RewardConfig;
import com.cali.config.Symbol;
import com.cali.dto.SymbolDefinitionDTO;

public class SymbolMapper {


    public static Symbol toSymbol(String symbol, SymbolDefinitionDTO dto) {

        return new Symbol(symbol, dto.type, new RewardConfig(dto.reward_multiplier, dto.extra));
    }
}
