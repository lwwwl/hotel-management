package com.example.hotelmanagement.model.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * Chatwoot会话meta详细信息BO
 * 对应meta字段，包含sender、assignee、channel、hmacVerified等
 */
@Data
public class ChatwootConversationMetaDetailBO implements Serializable {
    /**
     * 发送者信息，复用ChatwootContactDetailBO
     */
    @JsonProperty("sender")
    private ChatwootContactDetailBO sender;

    /**
     * 渠道类型，如Channel::WebWidget
     */
    @JsonProperty("channel")
    private String channel;

    /**
     * 会话分配人信息，复用ChatwootAssigneeBO
     */
    @JsonProperty("assignee")
    private ChatwootAssigneeBO assignee;

    /**
     * hmac校验标志
     */
    @JsonProperty("hmac_verified")
    private Boolean hmacVerified;
} 