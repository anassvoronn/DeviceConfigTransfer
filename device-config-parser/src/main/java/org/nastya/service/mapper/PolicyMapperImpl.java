package org.nastya.service.mapper;

import org.nastya.dto.PolicyAdditionalPropertyDto;
import org.nastya.dto.PolicyDto;
import org.nastya.dto.ServicesAndApplicationsDto;
import org.nastya.entity.Policy;
import org.nastya.entity.PolicyAdditionalProperty;
import org.nastya.entity.ServicesAndApplications;
import org.nastya.service.PolicyMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PolicyMapperImpl implements PolicyMapper {
    @Override
    public Policy mapDtoToEntity(PolicyDto dto) {
        Policy policy = new Policy();
        policy.setRuleId(dto.ruleId());
        policy.setAction(dto.action());
        policy.setBiDirectional(dto.biDirectional());
        policy.setComments(dto.comments());
        policy.setDeviceRuleNum(dto.deviceRuleNum());
        policy.setDst(dto.dst() != null ? dto.dst() : new ArrayList<>());
        policy.setDstNegate(dto.dstNegate());
        policy.setEnable(dto.enable());
        policy.setExpandedAction(dto.expandedAction());
        policy.setGlobalNat(dto.globalNat());
        policy.setLineNumber(dto.lineNumber());
        policy.setLoadBalancerRule(dto.loadBalancerRule());
        policy.setLog(dto.log());
        policy.setMapSourceToInterface(dto.mapSourceToInterface());
        policy.setMapSourceToSameAddress(dto.mapSourceToSameAddress());
        policy.setNat(dto.nat());
        policy.setObjectNatDestination(dto.objectNatDestination());
        policy.setObjectNatSource(dto.objectNatSource());
        policy.setRuleGrp(dto.ruleGrp());
        policy.setRuleName(dto.ruleName());
        policy.setRuleNum(dto.ruleNum());
        policy.setService(dto.service() != null ? dto.service() : new ArrayList<>());
        policy.setServiceNegate(dto.serviceNegate());
        policy.setSrc(dto.src() != null ? dto.src() : new ArrayList<>());
        policy.setSrcNegate(dto.srcNegate());
        policy.setTarget(dto.target() != null ? dto.target() : new ArrayList<>());
        policy.setUseNatPools(dto.useNatPools());

        PolicyAdditionalPropertyDto dtoProp = dto.additionalPropertyForPoliciesDto();

        List<ServicesAndApplications> servicesList = new ArrayList<>();
        if (dtoProp != null && dtoProp.servicesAndApplications() != null) {
            for (ServicesAndApplicationsDto dtoService : dtoProp.servicesAndApplications()) {
                ServicesAndApplications serviceEntity = new ServicesAndApplications(
                        dtoService.name(),
                        dtoService.type()
                );
                servicesList.add(serviceEntity);
            }
        }
        if (dtoProp != null) {
            PolicyAdditionalProperty additionalProperty = new PolicyAdditionalProperty(
                    dtoProp.layerIdentifier(),
                    dtoProp.originalRuleId(),
                    dtoProp.global(),
                    dtoProp.isLastRule(),
                    dtoProp.disableModifyRuleText(),
                    dtoProp.layerUid(),
                    dtoProp.content() != null ? dtoProp.content() : new ArrayList<>(),
                    dtoProp.layerType(),
                    dtoProp.overrideBySectionHeader(),
                    dtoProp.isDomainParentRule(),
                    dtoProp.install() != null ? dtoProp.install() : new ArrayList<>(),
                    dtoProp.vpn() != null ? dtoProp.vpn() : new ArrayList<>(),
                    dtoProp.sectionHeaderName(),
                    dtoProp.canonizedGws(),
                    dtoProp.orderedLayerIndex(),
                    dtoProp.parentRuleId(),
                    dtoProp.rulePosition(),
                    dtoProp.time() != null ? dtoProp.time() : new ArrayList<>(),
                    dtoProp.track(),
                    dtoProp.layerName(),
                    dtoProp.isParentRule(),
                    servicesList,
                    dtoProp.sectionHeader()
            );

            policy.getAdditionalProperties().add(additionalProperty);
        }

        return policy;
    }
}