package com.cali;

import com.cali.dto.GameConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class TestUtils {

    public static GameConfigDTO loadGameConfig(String path) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        try (InputStream is = contextClassLoader.getResourceAsStream(path)) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(is, GameConfigDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
