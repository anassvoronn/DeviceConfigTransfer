package org.nastya.kafka;

import lombok.extern.slf4j.Slf4j;
import org.nastya.dto.DeviceConfigChunk;
import org.nastya.exception.ConsumerProcessingException;
import org.nastya.service.ChunkAssembler;
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

    private final ChunkAssembler chunkAssembler;
    private final ZipService zipService;
    private final String outputDirectory;

    public DeviceConfigConsumer(ChunkAssembler assembler,
                                ZipService zipService,
                                @Value("${configs.output-directory}") String outputDirectory) {
        this.chunkAssembler = assembler;
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

            Optional<UUID> transferUuidOptional = chunkAssembler.addChunk(chunk);

            if (transferUuidOptional.isEmpty()) {
                return;
            }

            transferUuid = transferUuidOptional.get();

            Path zipFile = chunkAssembler.assemble(transferUuid);

            Path jsonFile = zipService.unzip(zipFile, Path.of(outputDirectory));

            log.info("Config [{}] saved to {}", jsonFile.getFileName(), jsonFile);
        } catch (Exception e) {
            throw new ConsumerProcessingException("Failed to process config file", e);
        } finally {
            if (transferUuid != null) {
                chunkAssembler.cleanup(transferUuid);
            }
        }
    }
}