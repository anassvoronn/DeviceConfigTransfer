package org.nastya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.nastya.enums.ServiceType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyService {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "comment")
    private String comment;
    @Column(name = "comments")
    private String comments;
    @Column(name = "members", columnDefinition = "jsonb")
    private List<String> members = new ArrayList<>();;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ServiceType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "service_definitions", columnDefinition = "jsonb")
    private List<ServiceDefinitions> serviceDefinitions = new ArrayList<>();
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_properties", columnDefinition = "jsonb")
    private List<ServiceAdditionalProperty> additionalProperty = new ArrayList<>();
}