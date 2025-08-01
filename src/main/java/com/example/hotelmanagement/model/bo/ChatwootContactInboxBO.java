package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootContactInboxBO implements Serializable {
    @JsonProperty("source_id")
    private String sourceId;
    @JsonProperty("inbox")
    private List<ChatwootInboxBO> inbox;
}
