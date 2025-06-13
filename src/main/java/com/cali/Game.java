package com.cali;

import com.cali.config.GameConfig;
import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.Symbol;

import java.util.*;

import static com.cali.config.LinearCombination.Combination.*;

public class Game {

    private final GameConfig gameConfig;

    public Game(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public void play(int betAmount) {
        String[][] scratchedCard = generateScratchMatrix();

        Map<String, Integer> symbolWithCount = extractValidRepeatingSymbols(scratchedCard);
        double totalPoints = 0;

        if (!symbolWithCount.isEmpty()) {
            LinearContentExtractor linearContentExtractor = new LinearContentExtractor();
            Set<String> verticalExtract = linearContentExtractor.verticalExtract(scratchedCard);
            Set<String> horizontalExtract = linearContentExtractor.horizontalExtract(scratchedCard);
            String lrDiagonal = linearContentExtractor.extractLeftToRightDiagonal(scratchedCard);
            String rlDiagonal = linearContentExtractor.extractRightToLeftDiagonal(scratchedCard);

            totalPoints = calculateStandardSymbolRewards(betAmount, symbolWithCount, verticalExtract, horizontalExtract, lrDiagonal, rlDiagonal);

            totalPoints = applyBonusEffects(scratchedCard, totalPoints);

        }

        System.out.println(totalPoints);
    }

    private double calculateStandardSymbolRewards(int betAmount, Map<String, Integer> symbolWithCount, Set<String> verticalExtract, Set<String> horizontalExtract, String lrDiagonal, String rlDiagonal) {
        double totalPoints = 0;
        for (Map.Entry<String, Integer> entry: symbolWithCount.entrySet()) {
            Symbol symbol = gameConfig.symbols.get(entry.getKey());
            RepeatingCombination repeatingCombination = gameConfig.repeatingCombinations.get(entry.getValue());

            RewardCalculator.Builder builder = new RewardCalculator
                    .Builder(symbol)
                    .withRepeatingCombination(repeatingCombination);

            if (verticalExtract.contains(entry.getKey())) {
                LinearCombination vertical = gameConfig.linearCombinationMap.get(VERTICALLY_LINEAR_SYMBOLS);
                builder.verticalCombination(vertical);
            }

            if (horizontalExtract.contains(entry.getKey())) {
                LinearCombination horizontal = gameConfig.linearCombinationMap.get(HORIZONTALLY_LINEAR_SYMBOLS);
                builder.horizontalCombination(horizontal);
            }

            if (entry.getKey().equals(lrDiagonal)) {
                LinearCombination lr = gameConfig.linearCombinationMap.get(LTR_DIAGONALLY_LINEAR_SYMBOLS);
                builder.leftToRightDiagonal(lr);
            }

            if (entry.getKey().equals(rlDiagonal)) {
                LinearCombination rl = gameConfig.linearCombinationMap.get(RTL_DIAGONALLY_LINEAR_SYMBOLS);
                builder.rightToLeftDiagonal(rl);
            }

            RewardCalculator rewardCalculator = builder.build();
            totalPoints += rewardCalculator.calculate(betAmount);
        }
        return totalPoints;
    }

    private String[][] generateScratchMatrix() {
        Matrix matrix = new Matrix(gameConfig.probabilities);
        return matrix.randomMatrixOf(gameConfig.rows, gameConfig.columns);
    }

    private Map<String, Integer> extractValidRepeatingSymbols(String[][] scratchedCard) {
        int minimumRepeatingTimes = getMinimumRepeatingTimes();
        RepeatingContentExtractor repeatingContentExtractor = new RepeatingContentExtractor(minimumRepeatingTimes, gameConfig.standardSymbols);
        return repeatingContentExtractor.extractRepeatingContent(scratchedCard);
    }


    private double applyBonusEffects(String[][] scratchedCard, double currentPoints) {
        List<Symbol> bonusSymbols = extractBonusSymbols(scratchedCard);

        for (Symbol bonus : bonusSymbols) {
            RewardCalculator bonusCalc = new RewardCalculator.Builder(bonus).build();
            currentPoints = bonusCalc.calculate(currentPoints);
        }

        return currentPoints;
    }

    private List<Symbol> extractBonusSymbols(String[][] scratchedCard) {
        List<Symbol> bonusSymbols = new ArrayList<>();

        for (String[] row : scratchedCard) {
            for (String cell : row) {
                if (gameConfig.bonusSymbols.contains(cell)) {
                    Symbol symbol = gameConfig.symbols.get(cell);
                    bonusSymbols.add(symbol);
                }
            }
        }

        return bonusSymbols;
    }

    private Integer getMinimumRepeatingTimes() {
        return gameConfig.repeatingCombinations.keySet().stream()
                .min(Comparator.comparingInt(e -> e)).get();
    }
}
