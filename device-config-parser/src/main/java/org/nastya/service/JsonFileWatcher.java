package org.nastya.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonFileWatcher {

    private final ImportService importService;

    @PostConstruct
    public void watchFolder() {
        Thread thread = new Thread(this::watch);
        thread.setDaemon(true);
        thread.start();
    }

    private void watch() {
        Path outputDir = Paths.get("/output");

        try {
            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }

            WatchService watchService = FileSystems.getDefault().newWatchService();

            outputDir.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE
            );

            log.info("Watching directory: {}", outputDir);

            while (true) {

                WatchKey key = watchService.take();

                for (WatchEvent<?> event : key.pollEvents()) {

                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {

                        Path fileName = (Path) event.context();

                        if (fileName.toString().endsWith(".json")) {

                            Path jsonFile = outputDir.resolve(fileName);

                            log.info("New JSON file detected: {}", jsonFile);

                            importService.importJson(jsonFile.toString());
                        }
                    }
                }

                key.reset();
            }

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to import JSON file", e);
        }
    }
}