package org.nastya.service.mapper;

import org.nastya.dto.HostAdditionalPropertyDto;
import org.nastya.dto.HostDto;
import org.nastya.entity.Host;
import org.nastya.entity.HostAdditionalProperty;
import org.nastya.service.HostMapper;
import org.springframework.stereotype.Component;

@Component
public class HostMapperImpl implements HostMapper {

    public Host mapDtoToEntity(HostDto dto) {
        Host host = new Host();
        host.setName(dto.name());
        host.setComment(dto.comment());
        host.setComments(dto.comments());
        host.setMembers(dto.members());
        host.setNegate(dto.isNegate());
        host.setFqdn(dto.fqdn());
        host.setType(dto.type());
        host.setIps(dto.ips());

        HostAdditionalPropertyDto dtoProp = dto.additionalProperties();
        if (dtoProp != null) {
            HostAdditionalProperty additionalProperty = new HostAdditionalProperty(
                    dtoProp.originalName(),
                    dtoProp.broadcast(),
                    Boolean.TRUE.equals(dtoProp.natAutoRule()),
                    Boolean.TRUE.equals(dtoProp.globalLevel()),
                    dtoProp.ckpType(),
                    dtoProp.natTarget(),
                    dtoProp.natIpv4(),
                    dtoProp.natMethod(),
                    dtoProp.location()
            );

            host.getAdditionalProperties().add(additionalProperty);
        }

        return host;
    }
}