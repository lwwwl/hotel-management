package com.example.hotelmanagement.model.request;

import java.util.List;

import lombok.Data;

/**
 * 保存快捷菜单排序请求
 */
@Data
public class QuickMenuSaveOrderRequest {
    private List<OrderItem> orders;

    @Data
    public static class OrderItem {
        private Long id;
        private Integer sortOrder;
    }
}


