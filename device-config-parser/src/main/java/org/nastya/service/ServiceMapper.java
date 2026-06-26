package org.nastya.service;

import org.nastya.dto.ServiceDto;
import org.nastya.entity.MyService;

public interface ServiceMapper {
    MyService mapDtoToEntity(ServiceDto dto);
}