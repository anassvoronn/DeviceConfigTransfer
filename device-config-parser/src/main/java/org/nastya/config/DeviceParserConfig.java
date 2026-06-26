package org.nastya.config;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeviceParserConfig{

    @Bean
    public ObjectMapper myObjectMapper() {
        JsonFactory factory = JsonFactory.builder()
                .disable(JsonFactory.Feature.FAIL_ON_SYMBOL_HASH_OVERFLOW)
                .build();

        return new ObjectMapper(factory);
    }
}