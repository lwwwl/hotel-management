package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootContactInboxBO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootContactCreateResponse implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("availability_status")
    private String availabilityStatus;
    @JsonProperty("payload")
    private List<ChatwootContactPayload> payload;

    @Data
    public static class ChatwootContactPayload implements Serializable {
        @JsonProperty("additional_attributes")
        private Map<String, Object> additionalAttributes;
        @JsonProperty("availability_status")
        private String availabilityStatus;
        private String email;
        private Long id;
        private String name;
        @JsonProperty("phone_number")
        private String phoneNumber;
        private Boolean blocked;
        private String identifier;
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

    private String error;
} 