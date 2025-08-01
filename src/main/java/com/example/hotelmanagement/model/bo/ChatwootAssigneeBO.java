package com.example.hotelmanagement.model.bo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootAssigneeBO implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("availability_status")
    private String availabilityStatus;
    @JsonProperty("auto_offline")
    private Boolean autoOffline;
    @JsonProperty("confirmed")
    private Boolean confirmed;
    @JsonProperty("email")
    private String email;
    @JsonProperty("available_name")
    private String availableName;
    @JsonProperty("name")
    private String name;
    @JsonProperty("role")
    private String role;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("custom_role_id")
    private Long customRoleId;
} 