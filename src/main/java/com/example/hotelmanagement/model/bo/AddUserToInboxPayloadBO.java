package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AddUserToInboxPayloadBO implements Serializable {
    private Long id;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("availability_status")
    private String availabilityStatus;

    @JsonProperty("auto_offline")
    private Boolean autoOffline;

    private Boolean confirmed;
    private String email;

    @JsonProperty("available_name")
    private String availableName;

    private String name;
    private String role;
    private String thumbnail;

    @JsonProperty("custom_role_id")
    private Long customRoleId;
}
