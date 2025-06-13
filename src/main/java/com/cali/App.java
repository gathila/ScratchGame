package com.cali;

import com.cali.config.GameConfig;
import com.cali.config.GameConfigFactory;
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
        MatrixProvider matrixProvider = new RandomMatrixProvider(config.probabilities);
        GameEngine gameEngine = new GameEngine(config, matrixProvider);

        gameEngine.play(100);
    }
}
