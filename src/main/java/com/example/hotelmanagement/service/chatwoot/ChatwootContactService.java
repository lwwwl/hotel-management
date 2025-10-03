package com.example.hotelmanagement.service.chatwoot;

import com.example.hotelmanagement.model.bo.ChatwootCreateContactRespBO;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactCreateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactUpdateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDetailRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactCreateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactUpdateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactDeleteResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactDetailResponse;

import java.util.Map;

public interface ChatwootContactService {

    ChatwootCreateContactRespBO createContact(ChatwootContactCreateRequest request);

    ChatwootContactUpdateResponse updateContact(ChatwootContactUpdateRequest request);

    ChatwootContactDeleteResponse deleteContact(ChatwootContactDeleteRequest request);

    ChatwootContactDetailResponse contactDetail(ChatwootContactDetailRequest request);
}
