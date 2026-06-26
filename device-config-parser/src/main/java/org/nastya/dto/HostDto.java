package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.nastya.enums.HostType;

import java.util.List;
import java.util.UUID;

public record HostDto(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("comment")
        String comment,
        @JsonProperty("comments")
        String comments,
        @JsonProperty("members")
        List<String> members,
        @JsonProperty("ips")
        List<String> ips,
        @JsonProperty("fqdn")
        String fqdn,
        @JsonProperty("is_negate")
        boolean isNegate,
        @JsonProperty("type")
        HostType type,
        @JsonProperty("additional_properties")
        HostAdditionalPropertyDto additionalProperties) {
}
