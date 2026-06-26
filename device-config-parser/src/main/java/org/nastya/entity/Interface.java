package org.nastya.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "interfaces")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interface {

    @Id
    private UUID id;

    private String name;
    private String mode;
    private String netmask;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "ips", columnDefinition = "jsonb")
    private List<String> ips;

    private String enable;
    private String hwdevice;
}