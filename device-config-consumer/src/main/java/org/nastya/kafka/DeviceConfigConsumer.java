package org.nastya.kafka;

import lombok.extern.slf4j.Slf4j;
import org.nastya.dto.DeviceConfigChunk;
import org.nastya.exception.ConsumerProcessingException;
import org.nastya.service.ChunkService;
import org.nastya.service.ZipService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class DeviceConfigConsumer {

    private final ChunkService assembler;
    private final ZipService zipService;
    private final String outputDirectory;

    public DeviceConfigConsumer(ChunkService assembler,
                                ZipService zipService,
                                @Value("${configs.output-directory}") String outputDirectory) {
        this.assembler = assembler;
        this.zipService = zipService;
        this.outputDirectory = outputDirectory;
    }

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

            Path jsonFile = zipService.unzip(zipFile, Path.of(outputDirectory));

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