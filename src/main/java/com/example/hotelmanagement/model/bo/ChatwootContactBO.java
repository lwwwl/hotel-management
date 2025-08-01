package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootContactBO implements Serializable {
    @JsonProperty("payload")
    private List<ChatwootContactDetailBO> payload;
} 