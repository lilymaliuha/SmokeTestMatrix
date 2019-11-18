package com.xyleme.bravais.datacontainers.configparametersofpreferencespage.usercommentsconfigs;

public enum CommentingPermissionParameter {
    READ("Read"),
    WRITE("Write"),
    ADMIN("Admin");

    private final String value;

    CommentingPermissionParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}