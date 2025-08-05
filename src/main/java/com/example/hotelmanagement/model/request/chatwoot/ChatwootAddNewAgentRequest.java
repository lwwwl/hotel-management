package com.example.hotelmanagement.model.request.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatwootAddNewAgentRequest {
    private String accessToken;
    /**
     * Full Name of the agent
     */
    private String name;
    
    /**
     * Email of the Agent
     */
    private String email;

    /**
     * Whether its administrator or agent.
     * agent/administrator
     */
    private String role;
    
    /**
     * The availability setting of the agent.
     */
    @JsonProperty("availability_status")
    private String availabilityStatus = "available";
    
    /**
     * Whether the availability status of agent is configured to go offline automatically when away.
     */
    @JsonProperty("auto_offline")
    private Boolean autoOffline = true;
} 