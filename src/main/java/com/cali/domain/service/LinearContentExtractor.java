package com.cali.domain.service;

import com.cali.config.CoveredArea;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class LinearContentExtractor {

    public Set<String> extractMatchingSymbols(String[][] matrix, List<CoveredArea> coveredAreas) {
        Set<String> matchingSymbols = new HashSet<>();

        for (CoveredArea area : coveredAreas) {
            List<Pair<Integer, Integer>> positions = area.getArea();
            if (positions == null || positions.isEmpty()) continue;

            Pair<Integer, Integer> first = positions.get(0);
            String firstSymbol = matrix[first.getLeft()][first.getRight()];

            boolean allMatch = positions.stream().skip(1).allMatch(pair -> {
                int row = pair.getLeft();
                int col = pair.getRight();
                return firstSymbol.equals(matrix[row][col]);
            });

            if (allMatch) {
                matchingSymbols.add(firstSymbol);
            }
        }

        return matchingSymbols;
    }

}
