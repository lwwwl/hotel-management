package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;

@Data
public class ChatwootAssigneeBO implements Serializable {
    private Long id;
    private Long accountId;
    private String availabilityStatus;
    private Boolean autoOffline;
    private Boolean confirmed;
    private String email;
    private String availableName;
    private String name;
    private String role;
    private String thumbnail;
    private Long customRoleId;
} 