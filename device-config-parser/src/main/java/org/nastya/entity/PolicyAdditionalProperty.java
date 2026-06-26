package org.nastya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyAdditionalProperty {
    private UUID layerIdentifier;
    private UUID originalRuleId;
    private String global;
    private boolean isLastRule;
    private String disableModifyRuleText;
    private UUID layerUid;
    private List<String> content;
    private String layerType;
    private boolean overrideBySectionHeader;
    private boolean isDomainParentRule;
    private List<String> install;
    private List<String> vpn;
    private String sectionHeaderName;
    private Map<String, String> canonizedGws;
    private int orderedLayerIndex;
    private String parentRuleId;
    private int rulePosition;
    private List<String> time;
    private String track;
    private String layerName;
    private boolean isParentRule;
    private List<ServicesAndApplications> servicesAndApplications;
    private UUID sectionHeader;
}