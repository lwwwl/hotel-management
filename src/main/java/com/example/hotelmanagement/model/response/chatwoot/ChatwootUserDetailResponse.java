package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootUserBO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootUserDetailResponse implements Serializable {
    private Long id;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("available_name")
    private String availableName;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private Boolean confirmed;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("message_signature")
    private String messageSignature;

    private String email;

    @JsonProperty("hmac_identifier")
    private String hmacIdentifier;

    @JsonProperty("inviter_id")
    private Long inviterId;

    private String name;
    private String provider;

    @JsonProperty("pubsub_token")
    private String pubsubToken;

    private String role;

    @JsonProperty("ui_settings")
    private Map<String, Object> uiSettings;

    private String uid;
    private String type;

    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;

    private List<ChatwootUserBO> accounts;

    // 错误信息
    private String error;
} 