package com.example.hotelmanagement.model.response;

import java.util.List;

import lombok.Data;

@Data
public class MenuListResponse {
    private List<MenuItem> menus;

    @Data
    public static class MenuItem {
        private Long menuId;
        private String menuName;
        private String icon;
        private Integer sortOrder;
        private Long parentId;
        private String path;
        private Integer type;
        private boolean visible;
    }
}


