package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServiceDefinitionsDto(
        @JsonProperty("protocol")
        String protocol,
        @JsonProperty("dst_port")
        String dstPort,
        @JsonProperty("src_port")
        String srcPort
) {
}