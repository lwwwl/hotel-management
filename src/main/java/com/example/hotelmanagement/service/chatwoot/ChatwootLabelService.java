package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddLabelRequest;

public interface ChatwootLabelService {

    /**
     * 为联系人添加标签
     */
    Boolean addLabel(ChatwootAddLabelRequest request);
}
