package org.nastya.repository;

import org.nastya.entity.MyService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "services")
public interface ServicesRepository extends JpaRepository<MyService, UUID> {
}