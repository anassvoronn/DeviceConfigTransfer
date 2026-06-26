package org.nastya.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final String topic;

    public KafkaProducerService(KafkaTemplate<String, byte[]> kafkaTemplate,
                                @Value("${kafka.topic.device-config}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(String fileName, byte[] content) {
        log.info(
                "Publishing file [{}] to topic [{}]",
                fileName,
                topic
        );

        kafkaTemplate.send(topic, fileName, content)
                .whenComplete((result, exception) -> {

                    if (exception != null) {

                        log.info(
                                "Failed to publish file [{}]",
                                fileName,
                                exception
                        );

                    } else {

                        log.info(
                                "File [{}] successfully published, offset [{}]",
                                fileName,
                                result.getRecordMetadata().offset()
                        );
                    }
                });
    }
}