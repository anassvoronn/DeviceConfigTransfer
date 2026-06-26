package org.nastya.service.importer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.nastya.service.JsonImporter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class AbstractImporter<D, E, R extends JpaRepository<E, UUID>> implements JsonImporter {

    protected static final int BATCH_SIZE = 100_000;

    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    protected AbstractImporter(ObjectMapper objectMapper,
                               EntityManager entityManager) {
        this.objectMapper = objectMapper;
        this.entityManager = entityManager;
    }

    @Override
    public void importData(JsonParser parser) {
        try {
            importJson(parser);
        } catch (IOException e) {
            throw new RuntimeException("Import json exception", e);
        }
    }

    private void importJson(JsonParser parser) throws IOException {
        if (parser.currentToken() != JsonToken.START_OBJECT) {
            parser.skipChildren();
            return;
        }

        List<E> batch = new ArrayList<>(BATCH_SIZE);
        int count = 0;
        int batchNumber = 0;

        while (parser.nextToken() != JsonToken.END_OBJECT) {
            parser.nextToken();

            D dto = objectMapper.readValue(parser, getDtoClass());
            E entity = map(dto);

            batch.add(entity);
            count++;

            if (batch.size() >= BATCH_SIZE) {
                batchNumber++;
                flush(batch, batchNumber);
            }
        }

        if (!batch.isEmpty()) {
            batchNumber++;
            flush(batch, batchNumber);
        }

        log.info("import finished. Total={}", count);
    }

    private void flush(List<E> batch, int batchNumber) {
        R repository = getRepository();
        repository.saveAll(batch);
        repository.flush();
        entityManager.clear();
        batch.clear();

        log.info("batch #{} flushed",
                batchNumber);
    }

    protected abstract Class<D> getDtoClass();

    protected abstract E map(D dto);

    protected abstract R getRepository();
}