package com.xyleme.bravais.datacontainers.customattributesdata;

public enum CABooleanTypeParameter {
    YES("Yes"),
    NO("No");

    private final String value;

    CABooleanTypeParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}