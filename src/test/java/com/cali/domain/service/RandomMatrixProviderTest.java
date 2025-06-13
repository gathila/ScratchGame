package com.cali.domain.service;

import com.cali.config.Probabilities;
import com.cali.config.StandardSymbolProbability;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RandomMatrixProviderTest {

    @Test
    void testGenerateMatrix_GeneratesMatrixWithCorrectDimensionsAndValidSymbols() {
        // Given: defined probabilities with known symbols
        Map<String, Integer> symbolProbabilities = Map.of(
                "A", 1,
                "B", 2,
                "C", 3
        );

        Map<String, Integer> bonusProbabilities = Map.of(
                "+500", 1
        );

        StandardSymbolProbability symbolProbability00 = new StandardSymbolProbability(0, 0, symbolProbabilities);
        StandardSymbolProbability symbolProbability01 = new StandardSymbolProbability(0, 1, symbolProbabilities);
        StandardSymbolProbability symbolProbability02 = new StandardSymbolProbability(0, 2, symbolProbabilities);
        StandardSymbolProbability symbolProbability10 = new StandardSymbolProbability(1, 0, symbolProbabilities);
        StandardSymbolProbability symbolProbability11 = new StandardSymbolProbability(1, 1, symbolProbabilities);
        StandardSymbolProbability symbolProbability12 = new StandardSymbolProbability(1, 2, symbolProbabilities);
        Map<Pair<Integer, Integer>, StandardSymbolProbability> pairStandardSymbolProbabilityMap = Map.of(Pair.of(0, 0), symbolProbability00,
                Pair.of(0, 1), symbolProbability01,
                Pair.of(0, 2), symbolProbability02,
                Pair.of(1, 0), symbolProbability10,
                Pair.of(1, 1), symbolProbability11,
                Pair.of(1, 2), symbolProbability12);
        Probabilities probabilities = new Probabilities(pairStandardSymbolProbabilityMap, bonusProbabilities);
        RandomMatrixProvider provider = new RandomMatrixProvider(probabilities);

        int rows = 2;
        int cols = 3;

        // When: we generate a matrix
        String[][] matrix = provider.generateMatrix(rows, cols);

        // Then: the dimensions should match
        assertEquals(rows, matrix.length);
        for (String[] row : matrix) {
            assertEquals(cols, row.length);
        }

        // And: all values should be one of the allowed symbols
        Set<String> allowedSymbols = symbolProbabilities.keySet();
        for (String[] row : matrix) {
            for (String cell : row) {
                if (!bonusProbabilities.containsKey(cell))
                    assertTrue(allowedSymbols.contains(cell),
                            "Unexpected symbol found: " + cell);
            }
        }
    }
}