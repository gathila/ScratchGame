package com.cali.infra;

import com.cali.dto.GameConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
}
