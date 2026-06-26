package org.nastya.repository;

import org.nastya.entity.Interface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "interfaces")
public interface InterfacesRepository extends JpaRepository<Interface, UUID> {
}