package com.cali;

import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.SymbolConfig;
import com.cali.config.WinCombination;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class RewardCalculator {

    private final SymbolConfig symbolConfig;
    private final RepeatingCombination repeatingCombination;
    private final List<LinearCombination> linearCombination;

    private RewardCalculator(Builder builder) {
        this.symbolConfig = builder.symbolConfig;
        this.repeatingCombination = builder.repeatingCombination;
        this.linearCombination = builder.linearCombination;
    }

    public double calculate(double betAmount) {
        Function<Double, Double> repeatingSymbol = this.symbolConfig.getRewardCalculator()
                .andThen(this.repeatingCombination.getRewardFunction());
        Optional<Function<Double, Double>> otherMatchings = linearCombination.stream().map(WinCombination::getRewardFunction)
                .reduce(Function::andThen);
        Function<Double, Double> finalCalculator = otherMatchings.map(repeatingSymbol::andThen)
                .orElse(repeatingSymbol);

        return finalCalculator.apply(betAmount);
    }

    public static class Builder {
        private final SymbolConfig symbolConfig;
        private final RepeatingCombination repeatingCombination;
        private final List<LinearCombination> linearCombination = new ArrayList<>();


        public Builder (SymbolConfig symbolConfig, RepeatingCombination repeatingCombination) {
            this.symbolConfig = symbolConfig;
            this.repeatingCombination = repeatingCombination;
        }


        public Builder verticalCombination(LinearCombination verticalCombination) {
            this.linearCombination.add(verticalCombination);
            return this;
        }

        public Builder horizontalCombination(LinearCombination horizontalCombination) {
            this.linearCombination.add(horizontalCombination);
            return this;
        }

        public Builder leftToRightDiagonal(LinearCombination leftToRightDiagonal) {
            this.linearCombination.add(leftToRightDiagonal);
            return this;
        }

        public Builder rightToLeftDiagonal(LinearCombination rightToLeftDiagonal) {
            this.linearCombination.add(rightToLeftDiagonal);
            return this;
        }

        public RewardCalculator build() {
            return new RewardCalculator(this);
        }
    }
}
