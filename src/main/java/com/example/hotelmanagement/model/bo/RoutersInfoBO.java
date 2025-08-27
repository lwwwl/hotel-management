package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RoutersInfoBO implements Serializable {
    private String path;
    private String name;
    private String component;
    private String perms;
    private String icon;
    private Integer sortOrder;
    private Boolean visible;
    private List<RoutersInfoBO> children;
}
