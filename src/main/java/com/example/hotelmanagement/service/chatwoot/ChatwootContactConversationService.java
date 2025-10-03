package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootGuestCreateConversationRespBO;
import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;

public interface ChatwootContactConversationService {
    GuestChatwootConversationListResponse getConversationList(GuestChatwootConversationListRequest request, Long guestId);

    ChatwootGuestCreateConversationRespBO createConversation(GuestChatwootCreateConversationRequest request);

    GuestChatwootConversationDetailResponse getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId);

    GuestChatwootUpdateLastSeenResponse updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId);
}
