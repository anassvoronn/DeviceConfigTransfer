package org.nastya.service.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.nastya.dto.InterfaceDto;
import org.nastya.entity.Interface;
import org.nastya.repository.InterfacesRepository;
import org.nastya.service.InterfaceMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InterfacesImporter extends AbstractImporter<InterfaceDto, Interface, InterfacesRepository>{


    private final InterfaceMapper interfaceMapper;
    private final InterfacesRepository interfacesRepository;

    public InterfacesImporter(ObjectMapper objectMapper,
                         EntityManager entityManager,
                         InterfaceMapper interfaceMapper,
                         InterfacesRepository interfacesRepository) {
        super(objectMapper, entityManager);
        this.interfaceMapper = interfaceMapper;
        this.interfacesRepository = interfacesRepository;
    }

    @Override
    protected Class<InterfaceDto> getDtoClass() {
        return InterfaceDto.class;
    }

    @Override
    protected Interface map(InterfaceDto dto) {
        return interfaceMapper.mapDtoToEntity(dto);
    }

    @Override
    protected InterfacesRepository getRepository() {
        return interfacesRepository;
    }

    @Override
    public Set<String> getSupportedJsonFields() {
        return Set.of("interfaces");
    }
}