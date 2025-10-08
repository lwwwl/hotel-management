package com.example.hotelmanagement.enums;

import lombok.Getter;

@Getter
public enum EventType {
    TASK("task"),
    CONVERSATION("conversation");

    private final String value;

    EventType(String value) {
        this.value = value;
    }
}
