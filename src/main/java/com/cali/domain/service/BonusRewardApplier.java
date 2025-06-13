package com.cali.domain.service;

import com.cali.config.Symbol;

import java.util.List;

public class BonusRewardApplier {

    public static double apply(List<Symbol> bonusSymbols, double baseReward) {
        double finalReward = baseReward;
        for (Symbol bonus : bonusSymbols) {
            finalReward = bonus.applyImpact(finalReward);
        }
        return finalReward;
    }
}
