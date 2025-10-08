package com.example.hotelmanagement.model.request;

import lombok.Data;

@Data
public class UserUpdateBasicRequest {
    private String displayName;
    private String phone;
    private String email;
    private String password;
}
