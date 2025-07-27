package com.example.hotelmanagement.model.response.chatwoot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatwootAddUserToAccountResponse {
    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("user_id")
    private Integer userId;

    private String role;

    // 错误信息
    private String error;
}
