package org.nastya.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PolicyAction {
    DROP("Drop"),
    ACCEPT("Accept");

    private final String label;

    PolicyAction(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static PolicyAction fromValue(String value) {
        for (PolicyAction action : PolicyAction.values()) {
            if (action.label.equalsIgnoreCase(value)) {
                return action;
            }
        }
        throw new IllegalArgumentException("Unknown PolicyAction: " + value);
    }
}