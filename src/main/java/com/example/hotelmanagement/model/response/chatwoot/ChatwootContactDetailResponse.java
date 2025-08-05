package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.example.hotelmanagement.model.bo.ChatwootContactInboxBO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Map;
import java.util.List;

@Data
public class ChatwootContactDetailResponse implements Serializable {
    @JsonProperty("payload")
    private ChatwootContactDetailBO payload;

    private String error;
} 