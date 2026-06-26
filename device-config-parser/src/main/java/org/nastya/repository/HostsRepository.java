package org.nastya.repository;

import org.nastya.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "hosts")
public interface HostsRepository extends JpaRepository<Host, UUID> {
}