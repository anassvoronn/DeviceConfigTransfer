package org.nastya.dto;

import lombok.Getter;

@Getter
public class TransferState {

    private final String fileName;
    private final int totalChunks;
    private int receivedChunks;

    public TransferState(
            String fileName,
            int totalChunks
    ) {
        this.fileName = fileName;
        this.totalChunks = totalChunks;
    }

    public void chunkReceived() {
        receivedChunks++;
    }

    public boolean completed() {
        return receivedChunks == totalChunks;
    }
}