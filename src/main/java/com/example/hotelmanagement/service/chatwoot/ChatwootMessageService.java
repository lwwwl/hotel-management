package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;
import java.util.Map;

public interface ChatwootMessageService {

    Map<String, Object> createMessage(ChatwootCreateMessageRequest request);

    Map<String, Object> getMessages(ChatwootGetMessagesRequest request);
}
