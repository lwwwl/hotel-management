package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootContactCreateResponse implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("availability_status")
    private String availabilityStatus;
    @JsonProperty("payload")
    private List<ChatwootContactDetailBO> payload;

    private String error;
} 