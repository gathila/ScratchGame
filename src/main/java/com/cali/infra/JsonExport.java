package com.cali.infra;

import com.cali.dto.GameResults;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonExport {

    public static void exportToJSON(GameResults result, String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
