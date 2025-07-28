package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;

public interface ChatwootMessageService {

    ChatwootCreateMessageResponse createMessage(ChatwootCreateMessageRequest request);

    ChatwootGetMessagesResponse getMessages(ChatwootGetMessagesRequest request);
}
