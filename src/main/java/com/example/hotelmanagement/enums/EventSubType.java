package com.example.hotelmanagement.enums;

import lombok.Getter;

@Getter
public enum EventSubType {
    CREATE("create"),
    CLAIM("claim"),
    COMPLETED("completed");

    private final String value;

    EventSubType(String value) {
        this.value = value;
    }
}
