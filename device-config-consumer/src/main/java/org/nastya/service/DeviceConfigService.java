package org.nastya.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
@Slf4j
public class DeviceConfigService {

    private final String outputDirectory;

    public DeviceConfigService(
            @Value("${configs.output-directory}") String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public Path getOutputDirectory() {
        return Path.of(outputDirectory);
    }

    public void logSavedConfig(Path configFile) {
        log.info("File [{}] saved to output", configFile.getFileName());
    }
}