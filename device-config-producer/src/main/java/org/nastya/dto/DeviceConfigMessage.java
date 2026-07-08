package org.nastya.dto;

public record DeviceConfigMessage(String fileName,
                                  byte[] content) {
}