package org.nastya.service;

import org.nastya.dto.PolicyDto;
import org.nastya.entity.Policy;

public interface PolicyMapper {

    Policy mapDtoToEntity(PolicyDto dto);
}