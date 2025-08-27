package com.example.hotelmanagement.model.request;

import lombok.Data;

import java.util.List;

@Data
public class RoleUpdateRequest {
    private Long roleId;
    private String name;
    private String description;
    private List<Long> menuIdList;
    private List<Long> userIdList;
}


