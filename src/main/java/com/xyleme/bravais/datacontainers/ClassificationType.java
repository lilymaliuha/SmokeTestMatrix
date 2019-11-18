package com.xyleme.bravais.datacontainers;

public enum ClassificationType {
    GENERAL("General"),
    COMPETENCY("Competency");

    final private String value;

    ClassificationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}