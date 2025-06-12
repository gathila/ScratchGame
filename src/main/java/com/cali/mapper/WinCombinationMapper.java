package com.cali.mapper;

import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.dto.WinCombinationDTO;

public class WinCombinationMapper {

    public static RepeatingCombination toRepeatingCombination(WinCombinationDTO dto) {
        return new RepeatingCombination(dto.group, dto.reward_multiplier, dto.count);
    }

    public static LinearCombination toLinearCombination(WinCombinationDTO dto) {
        return new LinearCombination(dto.group, dto.reward_multiplier);
    }
}
