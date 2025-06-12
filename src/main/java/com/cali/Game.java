package com.cali;

import com.cali.config.Configurations;
import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.SymbolConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Game {

    private Configurations configurations;
    private RepeatingContentExtractor repeatingContentExtractor;
    private LinearContentExtractor linearContentExtractor;

    public Game(Configurations configurations,
                RepeatingContentExtractor repeatingContentExtractor,
                LinearContentExtractor linearContentExtractor) {
        this.configurations = configurations;
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
                SymbolConfig symbolConfig = configurations.symbolConfigs.get(entry.getKey());
                RepeatingCombination repeatingCombination = configurations.repeatingCombinations.get(entry.getValue());

                RewardCalculator.Builder builder = new RewardCalculator
                        .Builder(symbolConfig, repeatingCombination);

                if (verticalExtract.contains(entry.getKey())) {
                    LinearCombination vertical = configurations.lenierCombinationMap.get("Vertical");
                    builder.verticalCombination(vertical);
                }

                if (horizontalExtract.contains(entry.getKey())) {
                    LinearCombination horizontal = configurations.lenierCombinationMap.get("Horizontal");
                    builder.horizontalCombination(horizontal);
                }

                if (entry.getKey().equals(lrDiagonal)) {
                    LinearCombination lr = configurations.lenierCombinationMap.get("LRDiagonal");
                    builder.horizontalCombination(lr);
                }

                if (entry.getKey().equals(rlDiagonal)) {
                    LinearCombination rl = configurations.lenierCombinationMap.get("RLDiagonal");
                    builder.horizontalCombination(rl);
                }

                RewardCalculator rewardCalculator = builder.build();
                totalPoints += rewardCalculator.calculate(betAmount);
            }
        }

        System.out.println(totalPoints);
    }
}
