package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.nastya.enums.ServiceType;

import java.util.List;

public record ServiceDto(
        @JsonProperty("name")
        String name,
        @JsonProperty("comment")
        String comment,
        @JsonProperty("comments")
        String comments,
        @JsonProperty("members")
        List<String> members,

        @JsonProperty("type")
        ServiceType type,
        @JsonProperty("service_definitions")
        List<ServiceDefinitionsDto> serviceDefinitions,
        @JsonProperty("additional_properties")
        ServiceAdditionalPropertyDto additionalPropertyDto
) {
}