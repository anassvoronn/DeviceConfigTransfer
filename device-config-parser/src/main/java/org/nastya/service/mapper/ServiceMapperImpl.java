package org.nastya.service.mapper;

import org.nastya.dto.ServiceDefinitionsDto;
import org.nastya.dto.ServiceDto;
import org.nastya.entity.MyService;
import org.nastya.entity.ServiceAdditionalProperty;
import org.nastya.entity.ServiceDefinitions;
import org.nastya.service.ServiceMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceMapperImpl implements ServiceMapper {
    @Override
    public MyService mapDtoToEntity(ServiceDto dto) {
        MyService service = new MyService();

        service.setName(dto.name());
        service.setComment(dto.comment());
        service.setComments(dto.comments());
        service.setMembers(dto.members());
        service.setType(dto.type());

        List<ServiceDefinitions> definitionsList = new ArrayList<>();
        if (dto.serviceDefinitions() != null) {
            for (ServiceDefinitionsDto defDto : dto.serviceDefinitions()) {
                ServiceDefinitions def = new ServiceDefinitions(
                        defDto.protocol(),
                        defDto.dstPort(),
                        defDto.srcPort()
                );
                definitionsList.add(def);
            }
        }
        service.setServiceDefinitions(definitionsList);

        List<ServiceAdditionalProperty> additionalList = new ArrayList<>();
        if (dto.additionalPropertyDto() != null) {
            ServiceAdditionalProperty additional = new ServiceAdditionalProperty(
                    dto.additionalPropertyDto().ckpType(),
                    dto.additionalPropertyDto().globalLevel(),
                    dto.additionalPropertyDto().originalDef()
            );
            additionalList.add(additional);
        }
        service.setAdditionalProperty(additionalList);

        return service;
    }
}