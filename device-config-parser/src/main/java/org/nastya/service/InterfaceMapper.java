package org.nastya.service;

import org.nastya.dto.InterfaceDto;
import org.nastya.entity.Interface;

public interface InterfaceMapper {

    Interface mapDtoToEntity(InterfaceDto dto);
}