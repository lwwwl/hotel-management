package com.example.hotelmanagement.model.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ChatwootAgentBO implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("account_id")
    private Long accountId;
    @JsonProperty("available_name")
    private String availableName;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("confirmed")
    private Boolean confirmed;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("message_signature")
    private String messageSignature;
    @JsonProperty("email")
    private String email;
    @JsonProperty("hmac_identifier")
    private String hmacIdentifier;
    @JsonProperty("inviter_id")
    private Long inviterId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("provider")
    private String provider;
    @JsonProperty("pubsub_token")
    private String pubsubToken;
    @JsonProperty("role")
    private String role;
    @JsonProperty("ui_settings")
    private Map<String, Object> uiSettings;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("type")
    private String type;
    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;
    @JsonProperty("accounts")
    private List<ChatwootUserBO> accounts;
} 