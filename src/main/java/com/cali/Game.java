package com.cali;

import com.cali.config.GameConfig;
import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.Symbol;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import static com.cali.config.LinearCombination.Combination.*;

public class Game {

    private final GameConfig gameConfig;

    public Game(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void play(int betAmount) {
        Matrix matrix = new Matrix(gameConfig.probabilities);
        String[][] scratchedCard = matrix.randomMatrixOf(gameConfig.rows, gameConfig.columns);

        Map<String, Integer> symbolWithCount = extractSymbolsWithRepeatingCounts(scratchedCard);
        double totalPoints = 0;

        if (!symbolWithCount.isEmpty()) {
            LinearContentExtractor linearContentExtractor = new LinearContentExtractor();
            Set<String> verticalExtract = linearContentExtractor.verticalExtract(scratchedCard);
            Set<String> horizontalExtract = linearContentExtractor.horizontalExtract(scratchedCard);
            String lrDiagonal = linearContentExtractor.extractLeftToRightDiagonal(scratchedCard);
            String rlDiagonal = linearContentExtractor.extractRightToLeftDiagonal(scratchedCard);


            for (Map.Entry<String, Integer> entry: symbolWithCount.entrySet()) {
                Symbol symbol = gameConfig.symbols.get(entry.getKey());
                RepeatingCombination repeatingCombination = gameConfig.repeatingCombinations.get(entry.getValue());

                RewardCalculator.Builder builder = new RewardCalculator
                        .Builder(symbol, repeatingCombination);

                if (verticalExtract.contains(entry.getKey())) {
                    LinearCombination vertical = gameConfig.lenierCombinationMap.get(VERTICALLY_LINEAR_SYMBOLS);
                    builder.verticalCombination(vertical);
                }

                if (horizontalExtract.contains(entry.getKey())) {
                    LinearCombination horizontal = gameConfig.lenierCombinationMap.get(HORIZONTALLY_LINEAR_SYMBOLS);
                    builder.horizontalCombination(horizontal);
                }

                if (entry.getKey().equals(lrDiagonal)) {
                    LinearCombination lr = gameConfig.lenierCombinationMap.get(LTR_DIAGONALLY_LINEAR_SYMBOLS);
                    builder.leftToRightDiagonal(lr);
                }

                if (entry.getKey().equals(rlDiagonal)) {
                    LinearCombination rl = gameConfig.lenierCombinationMap.get(RTL_DIAGONALLY_LINEAR_SYMBOLS);
                    builder.rightToLeftDiagonal(rl);
                }

                RewardCalculator rewardCalculator = builder.build();
                totalPoints += rewardCalculator.calculate(betAmount);
            }
        }

        System.out.println(totalPoints);
    }

    private Map<String, Integer> extractSymbolsWithRepeatingCounts(String[][] scratchedCard) {
        int minimumRepeatingTimes = getMinimumRepeatingTimes();
        RepeatingContentExtractor repeatingContentExtractor = new RepeatingContentExtractor(minimumRepeatingTimes, gameConfig.standardSymbols);
        return repeatingContentExtractor.extractRepeatingContent(scratchedCard);
    }

    private Integer getMinimumRepeatingTimes() {
        return gameConfig.repeatingCombinations.keySet().stream()
                .min(Comparator.comparingInt(e -> e)).get();
    }
}
