package com.example.hotelmanagement.service.chatwoot.impl;

import com.example.hotelmanagement.model.bo.ChatwootCreateContactRespBO;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactCreateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactUpdateRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactDetailRequest;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactCreateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactUpdateResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactDeleteResponse;
import com.example.hotelmanagement.model.response.chatwoot.ChatwootContactDetailResponse;
import com.example.hotelmanagement.service.chatwoot.ChatwootContactService;
import com.example.hotelmanagement.util.ChatwootHttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatwootContactServiceImpl implements ChatwootContactService {
    private static final Logger logger = LoggerFactory.getLogger(ChatwootContactServiceImpl.class);

    @Resource
    private ChatwootHttpClientUtil chatwootHttpClientUtil;

    @Value("${api.chatwoot.administrator.access.token}")
    private String apiAccessToken;

    @Value("${chatwoot.account.id}")
    private Long accountId;

    /**
     * Chatwoot侧创建contact唯一性检测：Email, Identifier, Phone number
     * <br>Identifier传随机字符串，其余传null，可以跳过检测。
     * @param request
     * @return
     */
    @Override
    public ChatwootCreateContactRespBO createContact(ChatwootContactCreateRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts";
        logger.info("调用Chatwoot创建联系人接口: {}", apiPath);
        try {
            ResponseEntity<JsonNode> response = chatwootHttpClientUtil.post(apiPath, request, JsonNode.class, apiAccessToken);
            logResponse(response);
            return parseCreateContactResponse(response);
        } catch (Exception e) {
            logger.error("调用Chatwoot创建联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public ChatwootContactUpdateResponse updateContact(ChatwootContactUpdateRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getId();
        logger.info("调用Chatwoot更新联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactUpdateResponse> response = chatwootHttpClientUtil.put(apiPath, request, ChatwootContactUpdateResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot更新联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactUpdateResponse errResponse = new ChatwootContactUpdateResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootContactDeleteResponse deleteContact(ChatwootContactDeleteRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getContactId();
        logger.info("调用Chatwoot删除联系人接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactDeleteResponse> response = chatwootHttpClientUtil.delete(apiPath, null, ChatwootContactDeleteResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot删除联系人接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactDeleteResponse errResponse = new ChatwootContactDeleteResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    @Override
    public ChatwootContactDetailResponse contactDetail(ChatwootContactDetailRequest request) {
        String apiPath = "/api/v1/accounts/" + accountId + "/contacts/" + request.getContactId();
        logger.info("调用Chatwoot获取联系人详情接口: {}", apiPath);
        try {
            ResponseEntity<ChatwootContactDetailResponse> response = chatwootHttpClientUtil.get(apiPath, null, null, ChatwootContactDetailResponse.class, apiAccessToken);
            logResponse(response);
            return response.getBody();
        } catch (Exception e) {
            logger.error("调用Chatwoot获取联系人详情接口失败: {}, error: {}", apiPath, e.getMessage(), e);
            ChatwootContactDetailResponse errResponse = new ChatwootContactDetailResponse();
            errResponse.setError(e.getMessage());
            return errResponse;
        }
    }

    private void logResponse(ResponseEntity<?> response) {
        logger.info("Chatwoot接口返回 - statusCode: {}, body: {}", 
                   response.getStatusCode(), 
                   response.getBody() != null ? response.getBody().toString() : "null");
    }

    private ChatwootCreateContactRespBO parseCreateContactResponse(ResponseEntity<JsonNode> response) {
        JsonNode body = response.getBody();
        if (body == null || body.isMissingNode() || body.isNull()) {
            logger.warn("Chatwoot响应body为空");
            return null;
        }

        JsonNode payload = body.path("payload");
        if (payload.isMissingNode() || !payload.isObject()) {
            logger.warn("Chatwoot响应body不包含payload或payload格式不正确");
            return null;
        }

        JsonNode contact = payload.path("contact");
        if (contact.isMissingNode() || !contact.isObject()) {
            logger.warn("Chatwoot响应payload不包含contact或contact格式不正确");
            return null;
        }

        JsonNode contactIdNode = contact.path("id");
        Long contactId = null;
        if (contactIdNode.isNumber()) {
            contactId = contactIdNode.asLong();
        }

        JsonNode contactInboxes = contact.path("contact_inboxes");
        String sourceId = null;
        if (contactInboxes.isArray() && !contactInboxes.isEmpty()) {
            JsonNode firstInbox = contactInboxes.get(0);
            if (firstInbox != null && !firstInbox.isMissingNode()) {
                JsonNode sourceIdNode = firstInbox.path("source_id");
                if (sourceIdNode.isTextual()) {
                    sourceId = sourceIdNode.asText();
                }
            }
        }

        if (contactId == null || sourceId == null) {
            logger.warn("无法从Chatwoot响应中解析contactId或sourceId");
            return null;
        }

        ChatwootCreateContactRespBO respBO = new ChatwootCreateContactRespBO();
        respBO.setContactId(contactId);
        respBO.setSourceId(sourceId);
        return respBO;
    }
    /*
     * 创建contact返回报文：
     * {
     *     "payload": {
     *         "contact": {
     *             "additional_attributes": {
     *                 "roomId": 2,
     *                 "roomName": "A102",
     *                 "checkInTime": 1754494411825,
     *                 "leaveTime": 1757172811000,
     *                 "type": "customer",
     *                 "age": 30
     *             },
     *             "availability_status": "offline",
     *             "email": null,
     *             "id": 6,
     *             "name": "Amy",
     *             "phone_number": null,
     *             "blocked": false,
     *             "identifier": "1234567890125",
     *             "thumbnail": "",
     *             "custom_attributes": {},
     *             "created_at": 1759386099,
     *             "contact_inboxes": [
     *                 {
     *                     "source_id": "b6bbceb2-db00-43f7-95e4-e599e12bd4f8",
     *                     "inbox": {
     *                         "id": 2,
     *                         "avatar_url": "",
     *                         "channel_id": 1,
     *                         "name": "Hotel-api",
     *                         "channel_type": "Channel::Api",
     *                         "provider": null
     *                     }
     *                 }
     *             ]
     *         },
     *         "contact_inbox": {
     *             "inbox": {
     *                 "id": 2,
     *                 "channel_id": 1,
     *                 "account_id": 1,
     *                 "name": "Hotel-api",
     *                 "created_at": "2025-08-06T14:21:36.697Z",
     *                 "updated_at": "2025-08-15T09:08:27.623Z",
     *                 "channel_type": "Channel::Api",
     *                 "enable_auto_assignment": true,
     *                 "greeting_enabled": false,
     *                 "greeting_message": "",
     *                 "email_address": null,
     *                 "working_hours_enabled": false,
     *                 "out_of_office_message": null,
     *                 "timezone": "UTC",
     *                 "enable_email_collect": true,
     *                 "csat_survey_enabled": false,
     *                 "allow_messages_after_resolved": true,
     *                 "auto_assignment_config": {},
     *                 "lock_to_single_conversation": false,
     *                 "portal_id": null,
     *                 "sender_name_type": "friendly",
     *                 "business_name": null,
     *                 "csat_config": {}
     *             },
     *             "source_id": "b6bbceb2-db00-43f7-95e4-e599e12bd4f8"
     *         }
     *     }
     * }
     */
}
