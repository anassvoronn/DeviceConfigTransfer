package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServicesAndApplicationsDto(
        @JsonProperty("name")
        String name,
        @JsonProperty("type")
        String type
) {
}