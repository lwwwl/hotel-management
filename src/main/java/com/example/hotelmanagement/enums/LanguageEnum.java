package com.example.hotelmanagement.enums;

/**
 * 语言枚举
 */
public enum LanguageEnum {
    ZH_CN("zh_CN", "中文简体"),
    EN_US("en_US", "English (US)"),
    JA_JP("ja_JP", "日本語");

    private final String code;
    private final String description;

    LanguageEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据代码获取枚举
     */
    public static LanguageEnum fromCode(String code) {
        for (LanguageEnum language : values()) {
            if (language.getCode().equals(code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("不支持的语言代码: " + code);
    }

    /**
     * 验证语言代码是否有效
     */
    public static boolean isValidCode(String code) {
        try {
            fromCode(code);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
