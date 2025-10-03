package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.request.chatwoot.*;
import com.example.hotelmanagement.model.response.chatwoot.*;

public interface ChatwootConversationService {

    ChatwootConversationCountResponse getConversationCount(ChatwootConversationCountRequest request);

    ChatwootConversationListResponse getConversationList(ChatwootConversationListRequest request);

    ChatwootCreateConversationResponse createConversation(ChatwootCreateConversationRequest request);

    ChatwootUpdateConversationResponse updateConversation(ChatwootUpdateConversationRequest request);

    ChatwootConversationDetailResponse getConversationDetail(ChatwootConversationDetailRequest request);

    ChatwootAddConversationLabelResponse addConversationLabel(ChatwootAddConversationLabelRequest request);

    boolean assignConversation(ChatwootAssignConversationRequest request, String userAccessToken, Long chatwootUserId);

    ChatwootUpdateConversationCustomAttributesResponse updateConversationCustomAttributes(ChatwootUpdateConversationCustomAttributesRequest request);

    boolean toggleConversationStatus(ChatwootToggleConversationStatusRequest request, String userAccessToken);
}
