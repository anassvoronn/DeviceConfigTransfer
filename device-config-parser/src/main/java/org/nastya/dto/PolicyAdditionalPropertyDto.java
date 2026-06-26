package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public record PolicyAdditionalPropertyDto(
        @JsonProperty("layer_identifier")
        UUID layerIdentifier,
        @JsonProperty("original_rule_id")
        UUID originalRuleId,
        @JsonProperty("global")
        String global,
        @JsonProperty("is_last_rule")
        boolean isLastRule,
        @JsonProperty("disableModifyRuleText")
        String disableModifyRuleText,
        @JsonProperty("layer_uid")
        UUID layerUid,
        @JsonProperty("content")
        List<String> content,
        @JsonProperty("layer_type")
        String layerType,
        @JsonProperty("override_by_section_header")
        boolean overrideBySectionHeader,
        @JsonProperty("is_domain_parent_rule")
        boolean isDomainParentRule,
        @JsonProperty("install")
        List<String> install,
        @JsonProperty("vpn")
        List<String> vpn,
        @JsonProperty("section_header_name")
        String sectionHeaderName,
        @JsonProperty("canonized_gws")
        Map<String, String> canonizedGws,
        @JsonProperty("ordered_layer_index")
        int orderedLayerIndex,
        @JsonProperty("parent_rule_id")
        String parentRuleId,
        @JsonProperty("rule_position")
        int rulePosition,
        @JsonProperty("time")
        List<String> time,
        @JsonProperty("track")
        String track,
        @JsonProperty("layer_name")
        String layerName,
        @JsonProperty("is_parent_rule")
        boolean isParentRule,
        @JsonProperty("services_and_applications")
        List<ServicesAndApplicationsDto> servicesAndApplications,
        @JsonProperty("section_header")
        UUID sectionHeader
) {
}