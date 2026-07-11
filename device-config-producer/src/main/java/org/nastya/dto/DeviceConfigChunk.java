package org.nastya.dto;

import java.util.UUID;

public record DeviceConfigChunk(
        String fileName,
        UUID transferId,
        int chunkIndex,
        int totalChunks,
        byte[] content
) {}