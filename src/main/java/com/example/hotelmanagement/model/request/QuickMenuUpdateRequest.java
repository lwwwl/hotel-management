package com.example.hotelmanagement.model.request;

import lombok.Data;

/**
 * 更新快捷菜单请求
 */
@Data
public class QuickMenuUpdateRequest {
    /** 主键ID */
    private Long id;
    /** 图标，可选 */
    private String icon;
    /** 内容（JSON字符串），可选 */
    private String content;
}


