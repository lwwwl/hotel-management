package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;

public interface GuestChatwootConversationService {
    GuestChatwootConversationListResponse getConversationList(GuestChatwootConversationListRequest request, Long guestId);
    GuestChatwootCreateConversationResponse createConversation(GuestChatwootCreateConversationRequest request, Long guestId);
    GuestChatwootConversationDetailResponse getConversationDetail(GuestChatwootConversationDetailRequest request, Long guestId);
    GuestChatwootUpdateLastSeenResponse updateLastSeen(GuestChatwootUpdateLastSeenRequest request, Long guestId);
}
