package com.example.hotelmanagement.model.response.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ChatwootContactUpdateResponse implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("payload")
    private List<ChatwootContactCreateResponse.ChatwootContactPayload> payload;

    private String error;
} 