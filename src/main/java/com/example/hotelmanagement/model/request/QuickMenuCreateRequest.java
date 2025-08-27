package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * 创建快捷菜单请求
 */
@Data
public class QuickMenuCreateRequest {
    /** 图标 */
    private String icon;
    /** 内容（JSON字符串，后端不做校验与解析） */
    private String content;
}


