package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootAgentBO implements Serializable {
    private Long id;
    private String accessToken;
    private Long accountId;
    private String availableName;
    private String avatarUrl;
    private Boolean confirmed;
    private String displayName;
    private String messageSignature;
    private String email;
    private String hmacIdentifier;
    private Long inviterId;
    private String name;
    private String provider;
    private String pubsubToken;
    private String role;
    private Map<String, Object> uiSettings;
    private String uid;
    private String type;
    private Map<String, Object> customAttributes;
    private List<ChatwootUserBO> accounts;
} 