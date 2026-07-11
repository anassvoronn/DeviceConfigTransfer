package org.nastya.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nastya.dto.DeviceConfigChunk;
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
        try {
            log.info("Received chunk {}/{} for [{}]", chunk.chunkIndex() + 1, chunk.totalChunks(), chunk.fileName());

            Optional<UUID> transfer = assembler.addChunk(chunk);

            if (transfer.isEmpty()) {
                return;
            }

            UUID transferId = transfer.get();

            Path zipFile = assembler.assemble(transferId);

            byte[] json = zipService.unzip(Files.readAllBytes(zipFile));

            service.saveConfig(chunk.fileName(), json);

            assembler.cleanup(transferId);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to process config file", e);
        }
    }
}