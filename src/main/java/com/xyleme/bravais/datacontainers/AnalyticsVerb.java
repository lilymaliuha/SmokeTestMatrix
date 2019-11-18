package com.xyleme.bravais.datacontainers;

public enum AnalyticsVerb {
    ANSWERED("answered"),
    ATTEMPTED("attempted"),
    COMMENTED("commented"),
    COMPLETED("completed"),
    CREATED("created"),
    DELETED("deleted"),
    DELETED_VERSION("deleted-version"),
    DOWNLOADED("downloaded"),
    EXITED("exited"),
    EXPERIENCED("experienced"),
    FAILED("failed"),
    IMPORTED("imported"),
    OPENED("opened"),
    PASSED("passed"),
    PROCTORED_IN("proctored-in"),
    PROCTORED_OUT("proctored-out"),
    RESUMED("resumed"),
    SUSPENDED("suspended"),
    UPDATED("updated");

    private final String value;

    AnalyticsVerb(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}