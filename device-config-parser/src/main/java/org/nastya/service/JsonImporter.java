package org.nastya.service;

import com.fasterxml.jackson.core.JsonParser;

import java.util.Set;

public interface JsonImporter {

    Set<String> getSupportedJsonFields();

    void importData(JsonParser parser);
}