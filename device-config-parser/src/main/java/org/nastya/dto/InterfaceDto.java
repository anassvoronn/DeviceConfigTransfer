package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record InterfaceDto(
        @JsonProperty("name")
        String name,
        @JsonProperty("mode")
        String mode,
        @JsonProperty("netmask")
        String netmask,
        @JsonProperty("ips")
        List<String> ips,
        @JsonProperty("enable")
        String enable,
        @JsonProperty("hwdevice")
        String hwdevice) {
}