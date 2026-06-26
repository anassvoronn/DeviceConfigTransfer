package org.nastya.service.importer;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.nastya.dto.PolicyDto;
import org.nastya.entity.Policy;
import org.nastya.repository.PoliciesRepository;
import org.nastya.service.PolicyMapper;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PoliciesImporter extends AbstractImporter<PolicyDto, Policy, PoliciesRepository> {

    private final PolicyMapper policyMapper;
    private final PoliciesRepository policiesRepository;

    public PoliciesImporter(ObjectMapper objectMapper,
                            EntityManager entityManager,
                            PolicyMapper policyMapper,
                            PoliciesRepository policiesRepository) {
        super(objectMapper, entityManager);
        this.policyMapper = policyMapper;
        this.policiesRepository = policiesRepository;
    }

    @Override
    public Set<String> getSupportedJsonFields() {
        return Set.of("policies");
    }

    @Override
    protected Class<PolicyDto> getDtoClass() {
        return PolicyDto.class;
    }

    @Override
    protected Policy map(PolicyDto dto) {
        return policyMapper.mapDtoToEntity(dto);
    }

    @Override
    protected PoliciesRepository getRepository() {
        return policiesRepository;
    }
}