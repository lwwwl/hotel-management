package com.example.hotelmanagement.model.bo;

import lombok.Data;

import java.util.List;

@Data
public class RoleListItemBO {
    private Long roleId;
    private String name;
    private String description;
    private Integer memberCount;
    private List<MenuInfoBO> menus;
}
