package org.nastya.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportService {

    private final ObjectMapper objectMapper;
    private final Map<String, JsonImporter> importerMap;

    @Transactional
    public void importJson(String path) {
        try (JsonParser parser = objectMapper
                .getFactory()
                .createParser(new File(path))) {

            log.info("Starting streaming import: {}", path);

            if (parser.nextToken() != JsonToken.START_OBJECT) {
                throw new IllegalStateException("Invalid JSON format");
            }

            while (parser.nextToken() != JsonToken.END_OBJECT) {

                String field = parser.currentName();
                parser.nextToken();

                JsonImporter importer = importerMap.get(field);

                if (importer != null) {
                    log.info("parsing field: {}", field);
                    importer.importData(parser);
                } else {
                    parser.skipChildren();
                }
            }

            log.info("Import finished successfully");

        } catch (Exception e) {
            log.error("Import failed", e);
            throw new RuntimeException("JSON import failed", e);
        }
    }
}