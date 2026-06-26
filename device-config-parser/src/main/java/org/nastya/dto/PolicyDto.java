package org.nastya.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.nastya.enums.PolicyAction;

import java.util.List;
import java.util.UUID;

public record PolicyDto(
        @JsonProperty("action")
        PolicyAction action,
        @JsonProperty("additional_properties")
        PolicyAdditionalPropertyDto additionalPropertyForPoliciesDto,
        @JsonProperty("bi-directional")
        int biDirectional,
        @JsonProperty("comments")
        String comments,
        @JsonProperty("device_rule_num")
        int deviceRuleNum,
        @JsonProperty("dst")
        List<String> dst,
        @JsonProperty("dst_negate")
        int dstNegate,
        @JsonProperty("enable")
        String enable,
        @JsonProperty("expandedAction")
        String expandedAction,
        @JsonProperty("global_nat")
        String globalNat,
        @JsonProperty("line_number")
        int lineNumber,
        @JsonProperty("load_balancer_rule")
        boolean loadBalancerRule,
        @JsonProperty("log")
        int log,
        @JsonProperty("map_source_to_interface")
        boolean mapSourceToInterface,
        @JsonProperty("map_source_to_same_address")
        boolean mapSourceToSameAddress,
        @JsonProperty("nat")
        String nat,
        @JsonProperty("object_nat_destination")
        boolean objectNatDestination,
        @JsonProperty("object_nat_source")
        boolean objectNatSource,
        @JsonProperty("rule_grp")
        String ruleGrp,
        @JsonProperty("rule_id")
        UUID ruleId,
        @JsonProperty("rule_name")
        String ruleName,
        @JsonProperty("rule_num")
        int ruleNum,
        @JsonProperty("service")
        List<String> service,
        @JsonProperty("service_negate")
        int serviceNegate,
        @JsonProperty("src")
        List<String> src,
        @JsonProperty("src_negate")
        int srcNegate,
        @JsonProperty("target")
        List<String> target,
        @JsonProperty("use_nat_pools")
        boolean useNatPools
) {
}