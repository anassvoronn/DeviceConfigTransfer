package org.nastya.service.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.nastya.dto.ServiceDto;
import org.nastya.entity.MyService;
import org.nastya.repository.ServicesRepository;
import org.nastya.service.ServiceMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ServicesImporter extends AbstractImporter<ServiceDto, MyService, ServicesRepository> {

    private final ServiceMapper serviceMapper;
    private final ServicesRepository servicesRepository;

    public ServicesImporter(ObjectMapper objectMapper,
                            EntityManager entityManager,
                            ServiceMapper serviceMapper,
                            ServicesRepository servicesRepository) {
        super(objectMapper, entityManager);
        this.serviceMapper = serviceMapper;
        this.servicesRepository = servicesRepository;
    }

    @Override
    public Set<String> getSupportedJsonFields() {
        return Set.of("services", "services_groups");
    }

    @Override
    protected Class<ServiceDto> getDtoClass() {
        return ServiceDto.class;
    }

    @Override
    protected MyService map(ServiceDto dto) {
        return serviceMapper.mapDtoToEntity(dto);
    }

    @Override
    protected ServicesRepository getRepository() {
        return servicesRepository;
    }
}