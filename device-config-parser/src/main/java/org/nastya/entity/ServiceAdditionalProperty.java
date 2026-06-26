package org.nastya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAdditionalProperty {
    private String ckpType;
    private String globalLevel;
    private String originalDef;
}