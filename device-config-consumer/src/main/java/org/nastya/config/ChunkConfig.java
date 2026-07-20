package org.nastya.config;

import org.nastya.service.ChunkAssembler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class ChunkConfig {
    @Bean
    public ChunkAssembler chunkAssembler(@Value("${configs.temp-directory}") String tempDirectory) {
        return new ChunkAssembler(Path.of(tempDirectory));
    }
}