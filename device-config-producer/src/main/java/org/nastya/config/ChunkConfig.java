package org.nastya.config;

import org.nastya.service.ChunkSplitter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunkConfig {
    @Bean
    public ChunkSplitter chunkSplitter(@Value("${configs.chunk-size}") int chunkSize) {
        return new ChunkSplitter(chunkSize);
    }
}