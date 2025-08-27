package com.example.hotelmanagement.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserWebInfoBO implements Serializable {
    private List<String> permissions;
    private List<Long> roles;
    private UserInfoBO user;
}
