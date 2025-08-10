package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;

import java.util.List;
import java.util.Map;

public interface ChatwootContactMessageService {
    Map<String, Object> getMessages(GuestChatwootGetMessagesRequest request);

    Map<String, Object> sendMessage(GuestChatwootSendMessageRequest request);

//    GuestChatwootUpdateMessageResponse updateMessage(GuestChatwootUpdateMessageRequest request);
}
