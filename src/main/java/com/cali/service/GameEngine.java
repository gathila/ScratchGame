package com.cali.service;

import com.cali.config.GameConfig;
import com.cali.config.LinearCombination;
import com.cali.config.RepeatingCombination;
import com.cali.config.Symbol;
import com.cali.domain.*;
import com.cali.domain.service.*;

import java.util.*;

import static com.cali.config.LinearCombination.LinearCombinationType.*;

public class GameEngine {

    private final GameConfig gameConfig;
    private final MatrixProvider matrixProvider;

    public GameEngine(GameConfig gameConfig, MatrixProvider matrixProvider) {
        this.gameConfig = gameConfig;
        this.matrixProvider = matrixProvider;
    }

    public Results play(double betAmount) {
        String[][] scratchedCard = matrixProvider.generateMatrix(gameConfig.rows, gameConfig.columns);

        Map<String, Integer> symbolWithCount = extractValidRepeatingSymbols(scratchedCard);
        Results results = new Results();
        results.setMatrix(scratchedCard);

        if (!symbolWithCount.isEmpty()) {
            LinearContentExtractor linearContentExtractor = new LinearContentExtractor();
            Set<String> verticalExtract = linearContentExtractor.verticalExtract(scratchedCard);
            Set<String> horizontalExtract = linearContentExtractor.horizontalExtract(scratchedCard);
            String lrDiagonal = linearContentExtractor.extractLeftToRightDiagonal(scratchedCard);
            String rlDiagonal = linearContentExtractor.extractRightToLeftDiagonal(scratchedCard);

            calculateStandardSymbolRewards(betAmount, symbolWithCount, verticalExtract, horizontalExtract, lrDiagonal, rlDiagonal, results);

            applyBonusEffects(scratchedCard, results);
            System.out.println(results);

        }

        return results;

    }


    private void calculateStandardSymbolRewards(final double betAmount, Map<String, Integer> symbolWithCount, Set<String> verticalExtract, Set<String> horizontalExtract, String lrDiagonal, String rlDiagonal, Results results) {
        double totalPoints = 0;
        Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
        for (Map.Entry<String, Integer> entry: symbolWithCount.entrySet()) {
            List<String> winningCombinations = new ArrayList<>();
            Symbol symbol = gameConfig.symbols.get(entry.getKey());
            RepeatingCombination repeatingCombination = gameConfig.repeatingCombinations.get(entry.getValue());
            winningCombinations.add(repeatingCombination.getCombinationName());

            List<LinearCombination> linearCombinations = new ArrayList<>();
            if (verticalExtract.contains(entry.getKey())) {
                LinearCombination vertical = gameConfig.linearCombinationMap.get(VERTICALLY_LINEAR_SYMBOLS);
                linearCombinations.add(vertical);
                winningCombinations.add(vertical.getCombinationName());
            }

            if (horizontalExtract.contains(entry.getKey())) {
                LinearCombination horizontal = gameConfig.linearCombinationMap.get(HORIZONTALLY_LINEAR_SYMBOLS);
                linearCombinations.add(horizontal);
                winningCombinations.add(horizontal.getCombinationName());
            }

            if (entry.getKey().equals(lrDiagonal)) {
                LinearCombination lr = gameConfig.linearCombinationMap.get(LTR_DIAGONALLY_LINEAR_SYMBOLS);
                linearCombinations.add(lr);
                winningCombinations.add(lr.getCombinationName());
            }

            if (entry.getKey().equals(rlDiagonal)) {
                LinearCombination rl = gameConfig.linearCombinationMap.get(RTL_DIAGONALLY_LINEAR_SYMBOLS);
                linearCombinations.add(rl);
                winningCombinations.add(rl.getCombinationName());
            }

            totalPoints += RewardCalculator.calculate(betAmount, symbol, repeatingCombination, linearCombinations);
            appliedWinningCombinations.put(entry.getKey(), winningCombinations);
        }

        results.setReward(totalPoints);
        results.setAppliedWinningCombinations(appliedWinningCombinations);
    }

    private Map<String, Integer> extractValidRepeatingSymbols(String[][] scratchedCard) {
        int minimumRepeatingTimes = getMinimumRepeatingTimes();
        RepeatingContentExtractor repeatingContentExtractor = new RepeatingContentExtractor(minimumRepeatingTimes, gameConfig.standardSymbols);
        return repeatingContentExtractor.extractRepeatingContent(scratchedCard);
    }

    private void applyBonusEffects(String[][] scratchedCard, Results results) {
        List<Symbol> bonusSymbols = extractBonusSymbols(scratchedCard);
        double rewards = BonusRewardApplier.apply(bonusSymbols, results.getReward());
        results.setReward(rewards);

        if (!bonusSymbols.isEmpty()) {
            results.setAppliedBonusSymbol(bonusSymbols.get(0).getSymbol());
        }
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
