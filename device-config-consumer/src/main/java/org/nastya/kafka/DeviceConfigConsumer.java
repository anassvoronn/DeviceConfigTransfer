package org.nastya.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nastya.dto.DeviceConfigChunk;
import org.nastya.exception.ConsumerProcessingException;
import org.nastya.service.ChunkAssembler;
import org.nastya.service.DeviceConfigService;
import org.nastya.service.ZipService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceConfigConsumer {

    private final ChunkAssembler assembler;
    private final ZipService zipService;
    private final DeviceConfigService service;

    @KafkaListener(
            topics = "${kafka.topic.device-config}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(DeviceConfigChunk chunk) {
        UUID transferUuid = null;
        try {
            log.info("Received chunk {}/{} for [{}]", chunk.chunkIndex() + 1, chunk.totalChunks(), chunk.fileName());

            Optional<UUID> transferUuidOptional = assembler.addChunk(chunk);

            if (transferUuidOptional.isEmpty()) {
                return;
            }

            transferUuid = transferUuidOptional.get();

            Path zipFile = assembler.assemble(transferUuid);

            Path jsonFile = zipService.unzip(zipFile, service.getOutputDirectory());

            service.logSavedConfig(jsonFile);

            log.info("Config [{}] saved to {}", jsonFile.getFileName(), jsonFile);
        } catch (Exception e) {
            throw new ConsumerProcessingException("Failed to process config file", e);
        } finally {
            if (transferUuid != null) {
                assembler.cleanup(transferUuid);
            }
        }
    }
}