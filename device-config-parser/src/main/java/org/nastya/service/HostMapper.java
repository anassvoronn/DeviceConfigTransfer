package org.nastya.service;

import org.nastya.dto.HostDto;
import org.nastya.entity.Host;

public interface HostMapper {

    Host mapDtoToEntity(HostDto dto);
}