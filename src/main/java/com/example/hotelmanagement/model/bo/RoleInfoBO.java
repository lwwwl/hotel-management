package com.example.hotelmanagement.model.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class RoleInfoBO implements Serializable {
    private Long id;
    private String name;
    private String description;
}
