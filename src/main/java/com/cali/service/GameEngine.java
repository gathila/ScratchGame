package com.cali.service;

import com.cali.config.*;
import com.cali.domain.service.*;
import com.cali.dto.GameResults;

import java.util.*;

import static com.cali.config.LinearCombination.LinearCombinationType.*;

public class GameEngine {

    private final GameConfig gameConfig;
    private final MatrixProvider matrixProvider;

    public GameEngine(GameConfig gameConfig, MatrixProvider matrixProvider) {
        this.gameConfig = gameConfig;
        this.matrixProvider = matrixProvider;
    }

    public GameResults play(double betAmount) {
        String[][] scratchedCard = matrixProvider.generateMatrix(gameConfig.getRows(), gameConfig.getColumns());

        Map<String, Integer> symbolWithCount = extractValidRepeatingSymbols(scratchedCard);
        GameResults gameResults = new GameResults();
        gameResults.setMatrix(scratchedCard);

        if (!symbolWithCount.isEmpty()) {


            Map<String, List<LinearCombination>> linearCombinationsMap = 
                    collectCombinations(scratchedCard, symbolWithCount.keySet());

            double reward = calculateRewards(betAmount, symbolWithCount, linearCombinationsMap);
            Map<String, List<String>> winningCombinations = collectCombinationInfo(symbolWithCount, linearCombinationsMap);

            gameResults.setReward(reward);
            gameResults.setAppliedWinningCombinations(winningCombinations);
            applyBonusEffects(scratchedCard, gameResults);
        }

        return gameResults;

    }

    private double calculateRewards(
            double betAmount,
            Map<String, Integer> symbolWithCount,
            Map<String, List<LinearCombination>> linearCombinationsMap) {

        double totalPoints = 0;
        for (Map.Entry<String, Integer> entry : symbolWithCount.entrySet()) {
            Symbol symbol = gameConfig.getSymbols().get(entry.getKey());
            RepeatingCombination repeatingCombination = gameConfig.getRepeatingCombinations().get(entry.getValue());
            List<LinearCombination> linearCombinations = linearCombinationsMap.getOrDefault(entry.getKey(), List.of());

            totalPoints += RewardCalculator.calculate(betAmount, symbol, repeatingCombination, linearCombinations);
        }
        return totalPoints;
    }

    private Map<String, List<String>> collectCombinationInfo(
            Map<String, Integer> symbolWithCount,
            Map<String, List<LinearCombination>> linearCombinationsMap) {

        Map<String, List<String>> result = new HashMap<>();

        for (Map.Entry<String, Integer> entry : symbolWithCount.entrySet()) {
            List<String> comboNames = new ArrayList<>();
            RepeatingCombination repeating = gameConfig.getRepeatingCombinations().get(entry.getValue());
            comboNames.add(repeating.getCombinationName());

            List<LinearCombination> linearCombinations = linearCombinationsMap.getOrDefault(entry.getKey(), List.of());
            for (LinearCombination linear : linearCombinations) {
                comboNames.add(linear.getCombinationName());
            }

            result.put(entry.getKey(), comboNames);
        }

        return result;
    }


    private Map<String, List<LinearCombination>> collectCombinations(
            String [][] scratchedCard,
            Set<String> matchedSymbols) {

        LinearContentExtractor extractor = new LinearContentExtractor();

        Map<LinearCombination.LinearCombinationType, Set<String>> typeToMatchedSymbols =
                new EnumMap<>(LinearCombination.LinearCombinationType.class);

        for (LinearCombination.LinearCombinationType type : LinearCombination.LinearCombinationType.values()) {
            LinearCombination combination = gameConfig.getLinearCombinationMap().get(type);
            if (combination == null) continue;

            List<CoveredArea> areas = combination.getCoveredAreas();
            Set<String> matched = extractor.extractMatchingSymbols(scratchedCard, areas);
            typeToMatchedSymbols.put(type, matched);
        }

        Map<String, List<LinearCombination>> result = new HashMap<>();

        for (String symbol : matchedSymbols) {
            List<LinearCombination> combinations = new ArrayList<>();

            for (Map.Entry<LinearCombination.LinearCombinationType, Set<String>> entry :
                    typeToMatchedSymbols.entrySet()) {
                if (entry.getValue().contains(symbol)) {
                    combinations.add(gameConfig.getLinearCombinationMap().get(entry.getKey()));
                }
            }

            if (!combinations.isEmpty()) {
                result.put(symbol, combinations);
            }
        }

        return result;
    }



    private Map<String, Integer> extractValidRepeatingSymbols(String[][] scratchedCard) {
        int minimumRepeatingTimes = getMinimumRepeatingTimes();
        RepeatingContentExtractor repeatingContentExtractor = new RepeatingContentExtractor(minimumRepeatingTimes, gameConfig.getStandardSymbols());
        return repeatingContentExtractor.extractRepeatingContent(scratchedCard);
    }

    private void applyBonusEffects(String[][] scratchedCard, GameResults gameResults) {
        List<Symbol> bonusSymbols = extractBonusSymbols(scratchedCard);
        double rewards = BonusRewardApplier.apply(bonusSymbols, gameResults.getReward());
        gameResults.setReward(rewards);

        if (!bonusSymbols.isEmpty()) {
            gameResults.setAppliedBonusSymbol(bonusSymbols.get(0).getSymbol());
        }
    }

    private List<Symbol> extractBonusSymbols(String[][] scratchedCard) {
        List<Symbol> bonusSymbols = new ArrayList<>();

        for (String[] row : scratchedCard) {
            for (String cell : row) {
                if (gameConfig.getBonusSymbols().contains(cell)) {
                    Symbol symbol = gameConfig.getSymbols().get(cell);
                    bonusSymbols.add(symbol);
                }
            }
        }

        return bonusSymbols;
    }

    private Integer getMinimumRepeatingTimes() {
        return gameConfig.getRepeatingCombinations().keySet().stream()
                .min(Comparator.comparingInt(e -> e)).get();
    }
}
