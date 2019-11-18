package com.xyleme.bravais.datacontainers.configparametersofpreferencespage;

public enum YesNoParameter {
    YES("Yes"),
    NO("No");

    private final String value;

    YesNoParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}