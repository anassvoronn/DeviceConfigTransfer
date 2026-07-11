package org.nastya.service;

import org.nastya.dto.DeviceConfigChunk;
import org.nastya.dto.TransferState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChunkAssembler {

    private final Map<UUID, TransferState> transfers = new ConcurrentHashMap<>();
    private final Path tempDirectory;

    public ChunkAssembler(
            @Value("${configs.temp-directory}") String tempDirectory) {
        this.tempDirectory = Path.of(tempDirectory);
    }

    public Optional<UUID> addChunk(DeviceConfigChunk chunk) {
        try {
            TransferState state = transfers.computeIfAbsent(chunk.transferId(), id -> new TransferState(
                            chunk.fileName(),
                            chunk.totalChunks()
                    )
            );

            Path transferDirectory = tempDirectory.resolve(chunk.transferId().toString());

            Files.createDirectories(transferDirectory);

            Path partFile = transferDirectory.resolve(String.format("%05d.part", chunk.chunkIndex()));

            Files.write(
                    partFile,
                    chunk.content(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            state.chunkReceived();

            if (state.completed()) {
                return Optional.of(chunk.transferId());
            }

            return Optional.empty();

        } catch (IOException e) {
            throw new IllegalStateException("Failed to save chunk", e);
        }
    }

    public Path assemble(UUID transferId) {
        try {
            Path transferDirectory = tempDirectory.resolve(transferId.toString());

            Path zipFile = transferDirectory.resolve("archive.zip");

            try (OutputStream out = Files.newOutputStream(zipFile)) {
                int index = 0;

                while (true) {
                    Path part = transferDirectory.resolve(String.format("%05d.part", index++));

                    if (!Files.exists(part)) {
                        break;
                    }

                    Files.copy(part, out);
                }
            }

            return zipFile;

        } catch (IOException e) {
            throw new IllegalStateException("Failed to assemble archive", e);
        }
    }

    public void cleanup(UUID transferId) {
        try {

            Path transferDirectory = tempDirectory.resolve(transferId.toString());

            if (!Files.exists(transferDirectory)) {
                return;
            }

            Files.walk(transferDirectory)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });

            transfers.remove(transferId);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to cleanup temporary files", e);
        }
    }
}