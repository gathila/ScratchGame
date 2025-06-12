package com.cali.config;

import com.cali.dto.GameConfigDTO;
import com.cali.infra.JsonLoader;
import com.cali.mapper.GameConfigMapper;

import java.io.IOException;

public class GameConfigFactory {

    public static GameConfig getInstance() {
        try {
            GameConfigDTO gameConfigDTO = JsonLoader.loadConfig("config.json");
            return new GameConfigMapper().toGameConfig(gameConfigDTO);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
