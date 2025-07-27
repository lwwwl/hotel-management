package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootUserBO implements Serializable {
    private int id;
    private String name;
    private String status;

    @JsonProperty("active_at")
    private String activeAt;

    private String role;
    private List<String> permissions;
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
