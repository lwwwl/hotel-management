package com.example.hotelmanagement.enums;

public enum ConversationLabelEnum {
    VERIFIED("verified"),
    UNVERIFIED("unverified");

    private String label;

    ConversationLabelEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ConversationLabelEnum getByLabel(String label) {
        for (ConversationLabelEnum conversationLabelEnum : ConversationLabelEnum.values()) {
            if (conversationLabelEnum.getLabel().equals(label)) {
                return conversationLabelEnum;
            }
        }
        return null;
    }
}
