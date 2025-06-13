package com.cali.config;

public enum ImpactType {

    MULTIPLY_REWARD, EXTRA_BONUS, MISS;

    public static ImpactType toImpactType(String impactType) {
        return valueOf(impactType.toUpperCase());
    }
}
