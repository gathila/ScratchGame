package com.cali.service;

import com.cali.TestUtils;
import com.cali.config.GameConfig;
import com.cali.domain.service.MatrixProvider;
import com.cali.dto.GameResults;
import com.cali.dto.GameConfigDTO;
import com.cali.mapper.GameConfigMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameEngineTest {


    private GameConfig config;

    @Mock
    private MatrixProvider matrixProvider;

    private GameEngine gameEngine;

    @BeforeEach
    void setup() {
        GameConfigDTO gameConfigDTO = TestUtils.loadGameConfig("config.json");
        this.config = GameConfigMapper.toGameConfig(gameConfigDTO);
        gameEngine = new GameEngine(config, matrixProvider);
    }

    @Test
    void testLossGame() {

        String[][] scratchedCard = noPattern();
        when(matrixProvider.generateMatrix(3, 3)).thenReturn(scratchedCard);
        GameResults gameResults = gameEngine.play(100);
        assertEquals(0.0, gameResults.getReward());
    }

    @Test
    void testRepeating3Times() {

        String[][] scratchedCard = repeat3Times();
        when(matrixProvider.generateMatrix(3, 3)).thenReturn(scratchedCard);
        double betAmount = 100;
        GameResults gameResults = gameEngine.play(betAmount);

        double multiplier = config.getSymbols().get("A").getRewardMultiplier();
        assertEquals(betAmount*multiplier, gameResults.getReward());
    }

    @Test
    void testCustomPattern1() {
        String[][] scratchedCard = customPattern1();
        when(matrixProvider.generateMatrix(3, 3)).thenReturn(scratchedCard);
        double betAmount = 100;
        GameResults gameResults = gameEngine.play(betAmount);

        assertEquals(3600, gameResults.getReward());
    }

    @Test
    void testCustomPattern2() {
        String[][] scratchedCard = customPattern2();
        when(matrixProvider.generateMatrix(3, 3)).thenReturn(scratchedCard);
        double betAmount = 100;
        GameResults gameResults = gameEngine.play(betAmount);

        assertEquals(3000, gameResults.getReward());
    }

    @Test
    void testPatternWithMissBonus() {

        String[][] scratchedCard = repeatsWithMissBonus();
        when(matrixProvider.generateMatrix(3, 3)).thenReturn(scratchedCard);
        double betAmount = 100;
        GameResults gameResults = gameEngine.play(betAmount);

        assertEquals(460, gameResults.getReward());
    }


    private String[][] noPattern() {
        String [][] arr = new String[3][3];
        arr[0][0] = "A";
        arr[0][1] = "A";
        arr[0][2] = "B";
        arr[1][0] = "B";
        arr[1][1] = "C";
        arr[1][2] = "C";
        arr[2][0] = "D";
        arr[2][1] = "D";
        arr[2][2] = "E";

        return arr;
    }

    private String[][] customPattern1() {
        String [][] arr = new String[3][3];
        arr[0][0] = "A";
        arr[0][1] = "A";
        arr[0][2] = "B";
        arr[1][0] = "A";
        arr[1][1] = "+1000";
        arr[1][2] = "B";
        arr[2][0] = "A";
        arr[2][1] = "A";
        arr[2][2] = "B";

        return arr;
    }

    private String[][] customPattern2() {
        String [][] arr = new String[3][3];
        arr[0][0] = "A";
        arr[0][1] = "B";
        arr[0][2] = "C";
        arr[1][0] = "E";
        arr[1][1] = "B";
        arr[1][2] = "10x";
        arr[2][0] = "F";
        arr[2][1] = "D";
        arr[2][2] = "B";

        return arr;
    }

    private String[][] repeat3Times() {
        String [][] arr = new String[3][3];
        arr[0][0] = "A";
        arr[0][1] = "A";
        arr[0][2] = "B";
        arr[1][0] = "A";
        arr[1][1] = "C";
        arr[1][2] = "C";
        arr[2][0] = "D";
        arr[2][1] = "D";
        arr[2][2] = "E";

        return arr;
    }

    private String[][] repeatsWithMissBonus() {
        String [][] arr = new String[3][3];
        arr[0][0] = "E";
        arr[0][1] = "F";
        arr[0][2] = "B";
        arr[1][0] = "F";
        arr[1][1] = "MISS";
        arr[1][2] = "F";
        arr[2][0] = "E";
        arr[2][1] = "E";
        arr[2][2] = "E";

        return arr;
    }
}