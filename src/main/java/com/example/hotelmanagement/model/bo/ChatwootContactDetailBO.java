package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootContactDetailBO implements Serializable {
    private Map<String, Object> additionalAttributes;
    private String availabilityStatus;
    private String email;
    private Long id;
    private String name;
    private String phoneNumber;
    private Boolean blocked;
    private String identifier;
    private String thumbnail;
    private Map<String, Object> customAttributes;
    private Long lastActivityAt;
    private Long createdAt;
    private List<ChatwootContactInboxBO> contactInboxes;
} 