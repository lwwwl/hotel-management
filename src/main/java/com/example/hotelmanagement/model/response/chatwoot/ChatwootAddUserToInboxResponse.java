package com.example.hotelmanagement.model.response.chatwoot;

import com.example.hotelmanagement.model.bo.AddUserToInboxPayloadBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatwootAddUserToInboxResponse implements Serializable {
    private List<AddUserToInboxPayloadBO> payload;

    // 错误信息
    private String error;
}
