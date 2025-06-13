package com.cali.domain.service;

import java.util.*;

public class LinearContentExtractor {

    public Set<String> verticalExtract(String[][] matrix) {
        Set<String> verticallyRepeatingLetters = new HashSet<>();

        for (int col = 0; col < matrix[0].length; col++) {
            String val = matrix[0][col];
            boolean allMatch = true;

            for (int row = 1; row < matrix.length; row++) {
                if (!val.equals(matrix[row][col])) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                verticallyRepeatingLetters.add(val);
            }
        }

        return verticallyRepeatingLetters;
    }

    public Set<String> horizontalExtract(String[][] matrix) {
        Set<String> horizontallyRepeatingLetters = new HashSet<>();

        for (int row = 0; row < matrix.length; row++) {
            String val = matrix[row][0];
            boolean allMatch = true;

            for (int col = 1; col < matrix[0].length; col++) {
                if (!val.equals(matrix[row][col])) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                horizontallyRepeatingLetters.add(val);
            }
        }

        return horizontallyRepeatingLetters;
    }


    public String extractLeftToRightDiagonal(String[][] matrix) {
        int size = Math.min(matrix.length, matrix[0].length);
        String first = matrix[0][0];

        for (int i = 1; i < size; i++) {
            if (!first.equals(matrix[i][i])) {
                return null; // Not all elements are equal
            }
        }

        return first;
    }


    public String extractRightToLeftDiagonal(String[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int size = Math.min(rows, cols);

        String first = matrix[0][cols - 1];

        for (int i = 1; i < size; i++) {
            if (!first.equals(matrix[i][cols - 1 - i])) {
                return null; // Not all elements are equal
            }
        }

        return first;
    }

}
