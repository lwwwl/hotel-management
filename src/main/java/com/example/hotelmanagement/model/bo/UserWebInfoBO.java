package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserWebInfoBO implements Serializable {
    private List<String> permissions;
    private List<RoleInfoBO> roles;
    private UserInfoBO user;
}
