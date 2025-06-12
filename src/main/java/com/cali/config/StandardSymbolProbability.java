package com.cali.config;

import java.util.Map;

public class StandardSymbolProbability {

    private final int column;
    private final int row;
    private final Map<String, Integer> symbols;

    public StandardSymbolProbability(int row, int column, Map<String, Integer> symbols) {
        this.row = row;
        this.column = column;
        this.symbols = symbols;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }
}
