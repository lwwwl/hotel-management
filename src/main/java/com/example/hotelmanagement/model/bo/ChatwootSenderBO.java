package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ChatwootSenderBO implements Serializable {
    @JsonProperty("additional_attributes")
    private ChatwootAdditionalAttributes additionalAttributes;
    
    @JsonProperty("custom_attributes")
    private Map<String, Object> customAttributes;
    
    @JsonProperty("email")
    private String email;

    @JsonProperty("avatar_url")
    private String avatarUrl;
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("identifier")
    private String identifier;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("phone_number")
    private String phoneNumber;
    
    @JsonProperty("thumbnail")
    private String thumbnail;
    
    @JsonProperty("blocked")
    private Boolean blocked;
    
    @JsonProperty("type")
    private String type;
}
