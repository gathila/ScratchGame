package com.cali.infra;

import com.cali.dto.GameConfigDTO;
import com.cali.dto.GameInputBet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class JsonLoader {

    public static GameConfigDTO loadConfig(String jsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = contextClassLoader.getResourceAsStream(jsonPath)) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: " + jsonPath);
            }

            return mapper.readValue(inputStream, GameConfigDTO.class);
        }
    }

    public static GameInputBet loadInput(String jsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(jsonPath), GameInputBet.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read file", e);
        }
    }
}
