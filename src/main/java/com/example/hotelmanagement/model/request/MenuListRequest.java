package com.example.hotelmanagement.model.request;

import lombok.Data;

@Data
public class MenuListRequest {
    // 预留扩展：例如按可见性或类型筛选
    private Boolean onlyVisible;
    private Integer type;
}


