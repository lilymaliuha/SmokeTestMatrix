package com.xyleme.bravais.datacontainers.configparametersofpreferencespage.usercommentsconfigs;

public enum SendEmailConfigParameter {
    DOCUMENT_ADMINS("Document Admins"),
    DOCUMENT_PUBLISHERS("Document Publishers"),
    USERS_THAT_HAVE_COMMENTED_ON_THAT_DOCUMENT("Users that have commented on that document");

    private final String value;

    SendEmailConfigParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}