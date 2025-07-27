package com.example.hotelmanagement.model.request.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class ChatwootContactUpdateRequest {
    @JsonProperty("account_id")
    private Long accountId;
    private Long id;
    private String name;
    private String email;
    private Boolean blocked;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String identifier;
    @JsonProperty("additional_attributes")
    private Map<String, Object> additionalAttributes;
    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;
} 