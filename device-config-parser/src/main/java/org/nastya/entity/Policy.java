package org.nastya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.nastya.enums.PolicyAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "policies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Policy {

    @Id
    @Column(name = "rule_id", nullable = false)
    private UUID ruleId;

    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private PolicyAction action;

    @Column(name = "bi_directional")
    private Integer biDirectional;

    @Column(name = "comments")
    private String comments;

    @Column(name = "device_rule_num")
    private Integer deviceRuleNum;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dst", columnDefinition = "jsonb")
    private List<String> dst = new ArrayList<>();

    @Column(name = "dst_negate")
    private Integer dstNegate;

    @Column(name = "enable")
    private String enable;

    @Column(name = "expanded_action")
    private String expandedAction;

    @Column(name = "global_nat")
    private String globalNat;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "load_balancer_rule")
    private Boolean loadBalancerRule;

    @Column(name = "log")
    private Integer log;

    @Column(name = "map_source_to_interface")
    private Boolean mapSourceToInterface;

    @Column(name = "map_source_to_same_address")
    private Boolean mapSourceToSameAddress;

    @Column(name = "nat")
    private String nat;

    @Column(name = "object_nat_destination")
    private Boolean objectNatDestination;

    @Column(name = "object_nat_source")
    private Boolean objectNatSource;

    @Column(name = "rule_grp")
    private String ruleGrp;

    @Column(name = "rule_name")
    private String ruleName;

    @Column(name = "rule_num")
    private Integer ruleNum;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "service", columnDefinition = "jsonb")
    private List<String> service = new ArrayList<>();

    @Column(name = "service_negate")
    private Integer serviceNegate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "src", columnDefinition = "jsonb")
    private List<String> src = new ArrayList<>();

    @Column(name = "src_negate")
    private Integer srcNegate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "target", columnDefinition = "jsonb")
    private List<String> target = new ArrayList<>();

    @Column(name = "use_nat_pools")
    private Boolean useNatPools;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_properties", columnDefinition = "jsonb")
    private List<PolicyAdditionalProperty> additionalProperties = new ArrayList<>();
}