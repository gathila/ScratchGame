package com.cali;

import com.cali.config.GameConfig;
import com.cali.config.GameConfigFactory;
import com.cali.dto.GameInputBet;
import com.cali.dto.GameResults;
import com.cali.infra.JsonExport;
import com.cali.infra.JsonLoader;
import com.cali.service.GameEngine;
import com.cali.domain.service.MatrixProvider;
import com.cali.domain.service.RandomMatrixProvider;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {


        GameConfig config = GameConfigFactory.getInstance();
        MatrixProvider matrixProvider = new RandomMatrixProvider(config.getProbabilities());

        GameInputBet input = JsonLoader.loadInput("input/input.json");

        GameEngine gameEngine = new GameEngine(config, matrixProvider);
        GameResults play = gameEngine.play(input.getBetAmount());
        JsonExport.exportToJSON(play, "output/result.json");
    }
}
