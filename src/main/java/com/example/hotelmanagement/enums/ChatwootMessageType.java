package com.example.hotelmanagement.enums;

/**
 * Chatwoot 消息类型
 * 1 = 入站/出站普通消息
 * 2 = 系统事件消息（例如指派、标签等）
 */
public enum ChatwootMessageType {
    NORMAL(1),
    SYSTEM(2);

    private final int code;

    ChatwootMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static boolean isNormal(Integer value) {
        return value != null && value == NORMAL.code;
    }
}


