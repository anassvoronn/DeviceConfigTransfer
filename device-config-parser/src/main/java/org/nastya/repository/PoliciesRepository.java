package org.nastya.repository;

import org.nastya.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "policies")
public interface PoliciesRepository extends JpaRepository<Policy, UUID> {
}