package org.nastya.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@Service
@Slf4j
public class DeviceConfigService {

    private final String outputDirectory;

    public DeviceConfigService(
            @Value("${configs.output-directory}") String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public void saveConfig(String fileName, byte[] content) {
        try {
            Path outputFile = Path.of(outputDirectory, fileName);

            Files.createDirectories(outputFile.getParent());

            Files.write(
                    outputFile,
                    content,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            log.info("File [{}] saved to output", fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save file " + fileName, e);
        }
    }
}