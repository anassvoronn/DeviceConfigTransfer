package org.nastya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.nastya.enums.HostType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hosts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Host {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String comment;
    private String comments;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "members", columnDefinition = "jsonb")
    private List<String> members = new ArrayList<>();

    private boolean isNegate;
    private String fqdn;

    @Enumerated(EnumType.STRING)
    private HostType type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ips", columnDefinition = "jsonb")
    private List<String> ips = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "additional_properties", columnDefinition = "jsonb")
    private List<HostAdditionalProperty> additionalProperties = new ArrayList<>();
}