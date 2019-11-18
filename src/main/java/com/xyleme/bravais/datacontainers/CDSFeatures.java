package com.xyleme.bravais.datacontainers;

public enum CDSFeatures {
    ADMIN_PORTAL("Admin Portal"),
    ANALYTICS("Analytics"),
    BRANDING("Branding"),
    CONTENT_ATTRIBUTES("Content Attributes"),
    FEATURES("Features"),
    PORTAL("Portal"),
    SYSTEM_SETTINGS("System Settings"),
    USER_MANAGEMENT("User Management"),
    USER_PROFILES("User Profiles");

    final String value;

    CDSFeatures(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}