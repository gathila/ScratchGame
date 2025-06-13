package com.cali.mapper;

import com.cali.config.CoveredArea;
import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.dto.WinCombinationDTO;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class WinCombinationMapper {

    public static RepeatingCombination toRepeatingCombination(String combinationName, WinCombinationDTO dto) {
        return new RepeatingCombination(combinationName, dto.reward_multiplier, dto.count);
    }

    public static LinearCombination toLinearCombination(String combinationName, WinCombinationDTO dto) {

        List<CoveredArea> coveredAreas = fromJsonCoveredAreas(dto.covered_areas);
        return new LinearCombination(combinationName, dto.group, dto.reward_multiplier, coveredAreas);
    }

    public static List<CoveredArea> fromJsonCoveredAreas(List<List<String>> coveredAreasJson) {
        List<CoveredArea> coveredAreas = new ArrayList<>();

        for (List<String> areaStrings : coveredAreasJson) {
            List<Pair<Integer, Integer>> positions = new ArrayList<>();
            for (String pos : areaStrings) {
                String[] parts = pos.split(":");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                positions.add(Pair.of(row, col));
            }
            coveredAreas.add(new CoveredArea(positions));
        }

        return coveredAreas;
    }
}
