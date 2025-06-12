package com.cali.config;

public enum SymbolType {

    STANDARD, BONUS;

    public static SymbolType toSymbolType(String type) {
        return valueOf(type.toUpperCase());
    }
}
