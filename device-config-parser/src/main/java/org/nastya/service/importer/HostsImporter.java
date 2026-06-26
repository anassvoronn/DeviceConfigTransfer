package org.nastya.service.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.nastya.dto.HostDto;
import org.nastya.entity.Host;
import org.nastya.repository.HostsRepository;
import org.nastya.service.HostMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HostsImporter extends AbstractImporter<HostDto, Host, HostsRepository> {

    private final HostMapper hostMapper;
    private final HostsRepository hostRepository;

    public HostsImporter(ObjectMapper objectMapper,
                         EntityManager entityManager,
                         HostMapper hostMapper,
                         HostsRepository hostRepository) {
        super(objectMapper, entityManager);
        this.hostMapper = hostMapper;
        this.hostRepository = hostRepository;
    }

    @Override
    public Set<String> getSupportedJsonFields() {
        return Set.of("hosts", "hosts_groups");
    }

    @Override
    protected Class<HostDto> getDtoClass() {
        return HostDto.class;
    }

    @Override
    protected Host map(HostDto dto) {
        return hostMapper.mapDtoToEntity(dto);
    }

    @Override
    protected HostsRepository getRepository() {
        return hostRepository;
    }
}