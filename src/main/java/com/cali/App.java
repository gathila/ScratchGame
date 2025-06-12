package com.cali;

import com.cali.config.GameConfig;
import com.cali.config.GameConfigFactory;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {


        GameConfig instance = GameConfigFactory.getInstance();
        Game game = new Game(instance);
    }
}
