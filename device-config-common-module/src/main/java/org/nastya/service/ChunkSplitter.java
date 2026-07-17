package org.nastya.service;

import lombok.extern.slf4j.Slf4j;
import org.nastya.dto.DeviceConfigChunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ChunkSplitter {
    private final int chunkSize;

    public ChunkSplitter(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public List<DeviceConfigChunk> split(String fileName, byte[] bytes) {
        UUID transferId = UUID.randomUUID();

        int totalChunks = (bytes.length + chunkSize - 1) / chunkSize;

        List<DeviceConfigChunk> result = new ArrayList<>();

        log.info("Chunk size = {}", chunkSize);

        for (int i = 0; i < totalChunks; i++) {

            int start = i * chunkSize;

            int end = Math.min(start + chunkSize, bytes.length);

            log.info("Chunk {} actual size = {}", i, end - start);

            result.add(new DeviceConfigChunk(
                    fileName,
                    transferId,
                    i,
                    totalChunks,
                    Arrays.copyOfRange(bytes, start, end)
            ));
        }

        return result;
    }
}