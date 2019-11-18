package com.xyleme.bravais.datacontainers;

public enum PermissionLevel {
    NONE("None"),
    BROWSE("Browse"),
    READ("Read"),
    WRITE("Write"),
    ADMIN("Admin");

    private String value;

    PermissionLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}