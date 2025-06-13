package com.cali.domain.service;

import com.cali.config.CoveredArea;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LinearContentExtractorTest {

    private final LinearContentExtractor extractor = new LinearContentExtractor();

    @Test
    void testHorizontalMatching() {
        String[][] matrix = {
                {"A", "A", "A"},
                {"B", "C", "D"},
                {"E", "F", "G"}
        };

        CoveredArea area = new CoveredArea(List.of(
                Pair.of(0, 0), Pair.of(0, 1), Pair.of(0, 2)
        ));

        Set<String> result = extractor.extractMatchingSymbols(matrix, List.of(area));
        assertEquals(Set.of("A"), result);
    }

    @Test
    void testVerticalMatching() {
        String[][] matrix = {
                {"B", "X", "C"},
                {"B", "Y", "D"},
                {"B", "Z", "E"}
        };

        CoveredArea area = new CoveredArea(List.of(
                Pair.of(0, 0), Pair.of(1, 0), Pair.of(2, 0)
        ));

        Set<String> result = extractor.extractMatchingSymbols(matrix, List.of(area));
        assertEquals(Set.of("B"), result);
    }

    @Test
    void testLeftToRightDiagonalMatching() {
        String[][] matrix = {
                {"X", "B", "C"},
                {"D", "X", "F"},
                {"G", "H", "X"}
        };

        CoveredArea area = new CoveredArea(List.of(
                Pair.of(0, 0), Pair.of(1, 1), Pair.of(2, 2)
        ));

        Set<String> result = extractor.extractMatchingSymbols(matrix, List.of(area));
        assertEquals(Set.of("X"), result);
    }

    @Test
    void testRightToLeftDiagonalMatching() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"D", "C", "F"},
                {"C", "H", "I"}
        };

        CoveredArea area = new CoveredArea(List.of(
                Pair.of(0, 2), Pair.of(1, 1), Pair.of(2, 0)
        ));

        Set<String> result = extractor.extractMatchingSymbols(matrix, List.of(area));
        assertEquals(Set.of("C"), result);
    }

    @Test
    void testNoMatchingSymbols() {
        String[][] matrix = {
                {"A", "B", "C"},
                {"D", "E", "F"},
                {"G", "H", "I"}
        };

        CoveredArea area = new CoveredArea(List.of(
                Pair.of(0, 0), Pair.of(0, 1), Pair.of(0, 2)
        ));

        Set<String> result = extractor.extractMatchingSymbols(matrix, List.of(area));
        assertTrue(result.isEmpty());
    }
}