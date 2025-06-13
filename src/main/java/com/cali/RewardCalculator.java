package com.cali;

import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.Symbol;
import com.cali.config.WinCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RewardCalculator {

    private final Function<Double, Double> rewardFunction;

    private RewardCalculator(Builder builder) {
        // Start with the symbol's reward function
        Function<Double, Double> composed = builder.symbol.getRewardFunction();

        // Chain the repeating combination if present
        if (builder.repeatingCombination != null) {
            composed = composed.andThen(builder.repeatingCombination.getRewardFunction());
        }

        // Chain all linear combinations
        for (LinearCombination combo : builder.linearCombinations) {
            composed = composed.andThen(combo.getRewardFunction());
        }

        this.rewardFunction = composed;
    }

    public double calculate(double betAmount) {
        return rewardFunction.apply(betAmount);
    }

    public static class Builder {
        private final Symbol symbol;
        private RepeatingCombination repeatingCombination;
        private final List<LinearCombination> linearCombinations = new ArrayList<>();

        public Builder(Symbol symbol) {
            this.symbol = symbol;
        }

        public Builder withRepeatingCombination(RepeatingCombination repeatingCombination) {
            this.repeatingCombination = repeatingCombination;
            return this;
        }

        public Builder verticalCombination(LinearCombination combination) {
            this.linearCombinations.add(combination);
            return this;
        }

        public Builder horizontalCombination(LinearCombination combination) {
            this.linearCombinations.add(combination);
            return this;
        }

        public Builder leftToRightDiagonal(LinearCombination leftToRightDiagonal) {
            this.linearCombinations.add(leftToRightDiagonal);
            return this;
        }

        public Builder rightToLeftDiagonal(LinearCombination rightToLeftDiagonal) {
            this.linearCombinations.add(rightToLeftDiagonal);
            return this;
        }

        public RewardCalculator build() {
            return new RewardCalculator(this);
        }

    }
}