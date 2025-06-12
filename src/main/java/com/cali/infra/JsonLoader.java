package com.cali.infra;

import com.cali.dto.GameConfigDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonLoader {

    public static GameConfigDTO loadConfig(String jsonPath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(jsonPath), GameConfigDTO.class);
    }
}
