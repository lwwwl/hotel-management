package com.example.hotelmanagement.model.response;

import com.example.hotelmanagement.model.bo.RoleListItemBO;
import lombok.Data;

import java.util.List;

@Data
public class RoleListResponse {
    private List<RoleListItemBO> roles;
}
