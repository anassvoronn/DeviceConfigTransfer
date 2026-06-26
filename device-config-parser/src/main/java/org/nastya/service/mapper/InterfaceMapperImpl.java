package org.nastya.service.mapper;

import org.nastya.dto.InterfaceDto;
import org.nastya.entity.Interface;
import org.nastya.service.InterfaceMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InterfaceMapperImpl implements InterfaceMapper {
    @Override
    public Interface mapDtoToEntity(InterfaceDto dto) {
        if (dto == null) {
            return null;
        }

        return new Interface(
                UUID.randomUUID(),
                dto.name(),
                dto.mode(),
                dto.netmask(),
                dto.ips(),
                dto.enable(),
                dto.hwdevice()
        );
    }
}