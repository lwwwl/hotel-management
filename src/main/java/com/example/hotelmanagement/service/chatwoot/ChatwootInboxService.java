package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootInboxMemberRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToInboxResponse;

public interface ChatwootInboxService {
    /**
     * 将用户关联到收件箱
     */
    ChatwootAddUserToInboxResponse addUserToInbox(ChatwootInboxMemberRequest request);


}
