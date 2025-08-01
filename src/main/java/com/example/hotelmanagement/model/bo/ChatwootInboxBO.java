package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ChatwootInboxBO implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("channel_id")
    private Long channelId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("channel_type")
    private String channelType;    
    @JsonProperty("provider")
    private String provider;
}
