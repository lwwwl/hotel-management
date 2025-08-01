package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootContactDetailBO implements Serializable {
    @JsonProperty("additional_attributes")
    private Map<String, Object> additionalAttributes;    
    @JsonProperty("availability_status")
    private String availabilityStatus;
    @JsonProperty("email")
    private String email;
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("phone_number_suffix")
    private String phoneNumberSuffix;
    @JsonProperty("blocked")
    private Boolean blocked;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("thumbnail")
    private String thumbnail;
    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;
    @JsonProperty("last_activity_at")
    private Long lastActivityAt;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("contact_inboxes")
    private List<ChatwootContactInboxBO> contactInboxes;
} 