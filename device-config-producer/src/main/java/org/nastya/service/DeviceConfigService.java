package org.nastya.service;

import lombok.extern.slf4j.Slf4j;
import org.nastya.kafka.KafkaProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class DeviceConfigService {
    private final KafkaProducerService kafkaProducer;
    private final String configsDirectory;

    public DeviceConfigService(
            KafkaProducerService kafkaProducer,
            @Value("${configs.input-directory}") String configsDirectory) {
        this.kafkaProducer = kafkaProducer;
        this.configsDirectory = configsDirectory;
    }

    public List<String> getAvailableConfigs() {
        log.info("Fetching available config files from directory [{}]", configsDirectory);

        try (Stream<Path> files = Files.list(Path.of(configsDirectory))) {

            List<String> configs = files
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.endsWith(".json"))
                    .toList();

            log.info("Found {} config file(s)", configs.size());

            return configs;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to access configs directory: " + configsDirectory,
                    e
            );
        }
    }

    public void sendConfig(String fileName) throws FileNotFoundException {
        log.info("Sending config file [{}] to Kafka", fileName);

        Path file = Path.of(configsDirectory, fileName);

        if (!Files.exists(file)) {
            throw new FileNotFoundException(fileName);
        }

        try {
            byte[] content = Files.readAllBytes(file);

            kafkaProducer.send(
                    fileName,
                    content
            );

            log.info(
                    "Config file [{}] successfully sent to Kafka, size [{} bytes]",
                    fileName,
                    content.length
            );

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to read file " + fileName,
                    e
            );
        }
    }
}