package com.dot.file.reader.persistence.model.enums.request;

public enum Duration {
    HOURLY("hourly"), DAILY("daily");

    private String code;

    Duration(String code) { this.code = code; }

    public String getCode() {
        return code;
    }
}

