package org.nastya.kafka;

import lombok.extern.slf4j.Slf4j;
import org.nastya.dto.DeviceConfigChunk;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, DeviceConfigChunk> kafkaTemplate;
    private final String topic;

    public KafkaProducerService(KafkaTemplate<String, DeviceConfigChunk> kafkaTemplate,
                                @Value("${kafka.topic.device-config}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(DeviceConfigChunk chunk) {
        log.info("Sending chunk {}/{} for file [{}]", chunk.chunkIndex() + 1, chunk.totalChunks(), chunk.fileName());

        kafkaTemplate.send(
                topic,
                chunk.transferId().toString(),
                chunk
        ).whenComplete((result, exception) -> {
            if (exception != null) {
                log.info("Failed to send chunk {}", chunk.chunkIndex(), exception);
                return;
            }
            log.info("Chunk {} sent successfully", chunk.chunkIndex());
        });
    }
}