package org.nastya.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.nastya.service.DeviceConfigService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceConfigConsumer {

    private final DeviceConfigService service;

    @KafkaListener(
            topics = "${kafka.topic.device-config}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ConsumerRecord<String, byte[]> record) {
        try {
            String fileName = record.key();
            byte[] content = record.value();

            log.info(
                    "Received file [{}], size [{} bytes]",
                    fileName,
                    content.length
            );

            service.saveConfig(fileName, content);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to process config file",
                    e
            );
        }
    }
}