package com.cali.domain.service;

import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.Symbol;
import com.cali.config.WinCombination;

import java.util.List;


public class RewardCalculator {


    public static double calculate(double currentPoints, Symbol symbol,
                            RepeatingCombination repeatingCombination,
                            List<LinearCombination> linearCombinations) {
        double baseMultiplier = symbol.getRewardMultiplier();
        double repeatingMultiplier = repeatingCombination != null ?
                repeatingCombination.getRewardMultiplier() : 1.0;
        double linearMultiplier = linearCombinations.stream()
                .map(WinCombination::getRewardMultiplier)
                .reduce(1.0, (a, b) -> a * b);

        return currentPoints * baseMultiplier * repeatingMultiplier * linearMultiplier;
    }
}