package com.cali;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RepeatingContentExtractor {

    private final int minimumNumberOfTimes;
    private final Set<String> repeatableSymbols;

    public RepeatingContentExtractor(int minimumNumberOfTimes, Set<String> repeatableSymbols) {
        this.minimumNumberOfTimes = minimumNumberOfTimes;
        this.repeatableSymbols = repeatableSymbols;
    }

    public Map<String, Integer> extractRepeatingContent(String [][] matrix) {

        Map<String, Integer> repeatTimes = new HashMap<>();
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix.length; j++) {
                if (repeatableSymbols.contains(matrix[i][j])) {
                    repeatTimes.compute(matrix[i][j], (k, v) -> {
                        if (v == null) {
                            return 1;
                        }
                        return v + 1;
                    });
                }
            }
        }

        return repeatTimes.entrySet().stream()
                .filter(e -> minimumNumberOfTimes <= e.getValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }
}
