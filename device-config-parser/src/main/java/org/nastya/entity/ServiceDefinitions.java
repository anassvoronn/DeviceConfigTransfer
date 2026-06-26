package org.nastya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDefinitions {
    private String protocol;
    private String dstPort;
    private String srcPort;
}