package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;

public interface GuestChatwootMessageService {
    GuestChatwootGetMessagesResponse getMessages(GuestChatwootGetMessagesRequest request, Long guestId);
    GuestChatwootSendMessageResponse sendMessage(GuestChatwootSendMessageRequest request, Long guestId);
    GuestChatwootUpdateMessageResponse updateMessage(GuestChatwootUpdateMessageRequest request, Long guestId);
}
