package org.nastya.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostAdditionalProperty {
    private String originalName;
    private String broadcast;
    private boolean natAutoRule;
    private boolean globalLevel;
    private String ckpType;
    private String natTarget;
    private String natIpv4;
    private String natMethod;
    private String location;
}