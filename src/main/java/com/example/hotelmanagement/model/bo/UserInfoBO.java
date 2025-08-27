package com.example.hotelmanagement.model.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserInfoBO implements Serializable {
    private Long id;
    private String username;
    private String displayName;
    private String employeeNumber;
    private String email;
    private String phone;
    private DeptInfoBO dept;
}
