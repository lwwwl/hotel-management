package com.example.hotelmanagement.model.request.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class GuestChatwootCreateConversationRequest {
    @JsonProperty("contact_identifier")
    private String contactIdentifier;
    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;
} 