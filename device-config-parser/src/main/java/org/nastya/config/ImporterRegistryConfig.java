package org.nastya.config;

import org.nastya.service.JsonImporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ImporterRegistryConfig {

    @Bean
    public Map<String, JsonImporter> importerMap(List<JsonImporter> importers) {
        Map<String, JsonImporter> map = new HashMap<>();

        for (JsonImporter importer : importers) {
            for (String key : importer.getSupportedJsonFields()) {
                map.put(key, importer);
            }
        }

        return map;
    }
}