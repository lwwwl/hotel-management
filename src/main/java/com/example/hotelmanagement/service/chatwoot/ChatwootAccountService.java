package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.ChatwootAccountUserRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootAddUserToAccountResponse;

public interface ChatwootAccountService {
    /**
     * 将用户关联到账户
     */
    ChatwootAddUserToAccountResponse addUserToAccount(ChatwootAccountUserRequest request);


}
