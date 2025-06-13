package com.cali.mapper;

import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.dto.WinCombinationDTO;

public class WinCombinationMapper {

    public static RepeatingCombination toRepeatingCombination(String combinationName, WinCombinationDTO dto) {
        return new RepeatingCombination(combinationName, dto.reward_multiplier, dto.count);
    }

    public static LinearCombination toLinearCombination(String combinationName, WinCombinationDTO dto) {
        return new LinearCombination(combinationName, dto.group, dto.reward_multiplier);
    }
}
