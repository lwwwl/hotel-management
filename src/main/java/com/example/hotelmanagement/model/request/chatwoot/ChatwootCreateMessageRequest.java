package com.example.hotelmanagement.model.request.chatwoot;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@Data
public class ChatwootCreateMessageRequest {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("conversation_id")
    private Long conversationId;
    private String content;
    @JsonProperty("message_type")
    private String messageType;
    // 请求不好转这个字段，先不传了
//    private Boolean isPrivate;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("content_attributes")
    private Map<String, Object> contentAttributes;
    @JsonProperty("campaign_id")
    private Long campaignId = null;
    @JsonProperty("template_params")
    private Map<String, Object> templateParams;
}