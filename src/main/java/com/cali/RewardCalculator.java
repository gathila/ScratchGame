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

    private final Symbol symbol;
    private final RepeatingCombination repeatingCombination;
    private final List<LinearCombination> linearCombination;

    private RewardCalculator(Builder builder) {
        this.symbol = builder.symbol;
        this.repeatingCombination = builder.repeatingCombination;
        this.linearCombination = builder.linearCombination;
    }

    public double calculate(double betAmount) {
        Function<Double, Double> repeatingSymbol = this.symbol.getRewardFunction()
                .andThen(this.repeatingCombination.getRewardFunction());
        Optional<Function<Double, Double>> otherMatchings = linearCombination.stream().map(WinCombination::getRewardFunction)
                .reduce(Function::andThen);
        Function<Double, Double> finalCalculator = otherMatchings.map(repeatingSymbol::andThen)
                .orElse(repeatingSymbol);

        return finalCalculator.apply(betAmount);
    }

    public static class Builder {
        private final Symbol symbol;
        private final RepeatingCombination repeatingCombination;
        private final List<LinearCombination> linearCombination = new ArrayList<>();


        public Builder (Symbol symbol, RepeatingCombination repeatingCombination) {
            this.symbol = symbol;
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
