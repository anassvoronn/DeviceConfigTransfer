package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HostAdditionalPropertyDto(
        @JsonProperty("original_name") String originalName,
        @JsonProperty("broadcast") String broadcast,
        @JsonProperty("nat-auto-rule") Boolean natAutoRule,
        @JsonProperty("global_level") Boolean globalLevel,
        @JsonProperty("ckp_type") String ckpType,
        @JsonProperty("nat-target") String natTarget,
        @JsonProperty("nat-ipv4") String natIpv4,
        @JsonProperty("nat-method") String natMethod,
        @JsonProperty("location") String location
) {
}