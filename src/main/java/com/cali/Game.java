package com.cali;

import com.cali.config.GameConfig;
import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.Symbol;

import java.util.Map;
import java.util.Set;

public class Game {

    private GameConfig gameConfig;
    private RepeatingContentExtractor repeatingContentExtractor;
    private LinearContentExtractor linearContentExtractor;

    public Game(GameConfig gameConfig,
                RepeatingContentExtractor repeatingContentExtractor,
                LinearContentExtractor linearContentExtractor) {
        this.gameConfig = gameConfig;
        this.repeatingContentExtractor = repeatingContentExtractor;
        this.linearContentExtractor = linearContentExtractor;
    }

    public void play(int betAmount) {
        Matrix matrix = new Matrix();
        String[][] scratchedCard = matrix.randomMetrixOf(3);

        Map<String, Integer> symbolWithCount = repeatingContentExtractor.extractRepeatingContent(scratchedCard);

        double totalPoints = 0;

        if (!symbolWithCount.isEmpty()) {
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
                    LinearCombination vertical = gameConfig.lenierCombinationMap.get("Vertical");
                    builder.verticalCombination(vertical);
                }

                if (horizontalExtract.contains(entry.getKey())) {
                    LinearCombination horizontal = gameConfig.lenierCombinationMap.get("Horizontal");
                    builder.horizontalCombination(horizontal);
                }

                if (entry.getKey().equals(lrDiagonal)) {
                    LinearCombination lr = gameConfig.lenierCombinationMap.get("LRDiagonal");
                    builder.horizontalCombination(lr);
                }

                if (entry.getKey().equals(rlDiagonal)) {
                    LinearCombination rl = gameConfig.lenierCombinationMap.get("RLDiagonal");
                    builder.horizontalCombination(rl);
                }

                RewardCalculator rewardCalculator = builder.build();
                totalPoints += rewardCalculator.calculate(betAmount);
            }
        }

        System.out.println(totalPoints);
    }
}
