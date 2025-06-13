package com.cali.domain.service;

import com.cali.config.Probabilities;
import com.cali.domain.Matrix;

public class RandomMatrixProvider implements MatrixProvider {

    private final Probabilities probabilities;
    public RandomMatrixProvider(Probabilities probabilities) {
        this.probabilities = probabilities;
    }

    @Override
    public String[][] generateMatrix(int rows, int cols) {
        return new Matrix(probabilities).randomMatrixOf(rows, cols);
    }
}
