package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootUserBO implements Serializable {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("status")
    private String status;

    @JsonProperty("active_at")
    private String activeAt;

    @JsonProperty("role")
    private String role;
    @JsonProperty("permissions")
    private List<String> permissions;
    @JsonProperty("availability")
    private String availability;

    @JsonProperty("availability_status")
    private String availabilityStatus;

    @JsonProperty("auto_offline")
    private boolean autoOffline;

    @JsonProperty("custom_role_id")
    private int customRoleId;

    @JsonProperty("custom_role")
    private Map<String, Object> customRole;
}
