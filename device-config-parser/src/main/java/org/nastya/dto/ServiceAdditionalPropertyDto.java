package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServiceAdditionalPropertyDto(
        @JsonProperty("ckp_type")
        String ckpType,
        @JsonProperty("global_level")
        String globalLevel,
        @JsonProperty("original_def")
        String originalDef
) {
}