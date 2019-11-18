package com.xyleme.bravais.datacontainers.channelconfigurationdata;

public enum SortingOption {
    BY_RELEVANCE("By relevance"),
    BY_DOCUMENT_TITLE_A_Z("By document title A-Z"),
    BY_DOCUMENT_TITLE_Z_A("By document title Z-A"),
    NEWEST_DOCUMENTS_FIRST("Newest documents first"),
    OLDEST_DOCUMENTS_FIRST("Oldest documents first");

    private final String value;

    SortingOption(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}