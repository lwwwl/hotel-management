package com.example.hotelmanagement.service.impl;

import com.example.hotelmanagement.dao.entity.HotelGuest;
import com.example.hotelmanagement.dao.repository.HotelGuestRepository;
import com.example.hotelmanagement.dao.repository.HotelRoomRepository;
import com.example.hotelmanagement.dao.entity.HotelRoom;
import com.example.hotelmanagement.enums.ConversationLabelEnum;
import com.example.hotelmanagement.model.bo.ChatwootAdditionalAttributes;
import com.example.hotelmanagement.model.bo.ChatwootContactDetailBO;
import com.example.hotelmanagement.model.bo.ChatwootContactInboxBO;
import com.example.hotelmanagement.model.request.HotelGuestCreateRequest;
import com.example.hotelmanagement.model.request.HotelGuestUpdateRequest;
import com.example.hotelmanagement.model.request.HotelGuestDeleteRequest;
import com.example.hotelmanagement.model.request.HotelGuestDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddLabelRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootContactCreateRequest;
import com.example.hotelmanagement.model.response.ResponseResult;
import com.example.hotelmanagement.service.ChatwootGuestFacadeService;
import com.example.hotelmanagement.service.HotelGuestService;
import com.example.hotelmanagement.service.chatwoot.ChatwootLabelService;
import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class HotelGuestServiceImpl implements HotelGuestService {

    private static final Logger logger = LoggerFactory.getLogger(HotelGuestServiceImpl.class);

    @Resource
    private HotelGuestRepository hotelGuestRepository;

    @Resource
    private ChatwootGuestFacadeService chatwootGuestFacadeService;

    @Resource
    private HotelRoomRepository hotelRoomRepository;

    @Resource
    private ChatwootLabelService chatwootLabelService;

    @Value("${chatwoot.inbox.id}")
    private Long chatwootInboxId;

    @Value("${chatwoot.account.id}")
    private Long chatwootAccountId;

    @Override
    @Transactional
    public ResponseEntity<?> createGuest(HotelGuestCreateRequest request) {
        // todo 请求酒店服务获取用户入住时间等信息
        Long checkInTime = System.currentTimeMillis();
        Long leaveTime = null;
        String phoneNumber = null;

        // todo 目前所有请求都创建新guest，之后要做判断，若guest表中存在同样的checkInTime+phoneNumber或checkInTime+name，则返回已有数据。

        // 获取房间信息
        HotelRoom room = null;
        if (StringUtils.hasText(request.getRoomName())) {
            room = hotelRoomRepository.findByName(request.getRoomName()).orElse(null);
        }

        // 创建chatwoot账号
        // phoneNumbere, email都传空，chatwoot会依据identifier做唯一性验证
        ChatwootContactCreateRequest chatwootContactCreateRequest = new ChatwootContactCreateRequest();
        chatwootContactCreateRequest.setInboxId(chatwootInboxId);
        chatwootContactCreateRequest.setName(request.getGuestName());
        chatwootContactCreateRequest.setPhoneNumber(null);
        chatwootContactCreateRequest.setEmail(null);
        chatwootContactCreateRequest.setBlocked(false);
        chatwootContactCreateRequest.setAvatarUrl(null);
        chatwootContactCreateRequest.setIdentifier(UUID.randomUUID().toString());

        // 设置chatwoot额外属性
        ChatwootAdditionalAttributes additionalAttributes = new ChatwootAdditionalAttributes();
        if (room != null) {
            additionalAttributes.setRoomId(room.getId());
            additionalAttributes.setRoomName(room.getName());
        }
        additionalAttributes.setCheckInTime(checkInTime);
        additionalAttributes.setLeaveTime(leaveTime);

        chatwootContactCreateRequest.setAdditionalAttributes(additionalAttributes);

        ChatwootContactDetailBO chatwootContactDetailBO = chatwootGuestFacadeService.createContact(chatwootContactCreateRequest);
        if (chatwootContactDetailBO == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Chatwoot contact 创建失败");
        }
        Long contactId = chatwootContactDetailBO.getId();
        String sourceId = safeGetSourceId(chatwootContactDetailBO);

        ChatwootAddLabelRequest chatwootAddLabelRequest = new ChatwootAddLabelRequest();
        chatwootAddLabelRequest.setContactId(contactId);
        chatwootAddLabelRequest.setLabels(List.of(ConversationLabelEnum.UNVERIFIED.getLabel()));
        Boolean addLabelResult = chatwootLabelService.addLabel(chatwootAddLabelRequest);
        if (!addLabelResult) {
            logger.error("contactId:{} 添加label失败", contactId);
        }
        // 保存客人信息
        HotelGuest guest = new HotelGuest();
        guest.setGuestName(request.getGuestName());
        guest.setRoomName(room == null ? null : room.getName());
        guest.setPhoneSuffix(request.getPhoneSuffix());
        guest.setChatwootContactId(contactId);
        guest.setChatwootSourceId(sourceId);
        guest.setCheckInTime(new Timestamp(checkInTime));
        guest.setLeaveTime(new Timestamp(leaveTime));
        guest.setVerify(Boolean.FALSE);
        guest.setCreateTime(new Timestamp(System.currentTimeMillis()));
        guest.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        hotelGuestRepository.save(guest);
        return ResponseEntity.ok(guest);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateGuest(HotelGuestUpdateRequest request) {
        HotelGuest hotelGuest = hotelGuestRepository.findById(request.getGuestId()).orElse(null);
        if (hotelGuest == null) {
            return ResponseEntity.ok().body(ResponseResult.fail("Guest not found"));
        }
        if (request.getVerify() == null) {
            return ResponseEntity.ok().body(ResponseResult.fail("Invalid verify"));
        }
        List<String> labels = null;
        if (request.getVerify()) {
            labels = List.of(ConversationLabelEnum.VERIFIED.getLabel());
        } else {
            labels = List.of(ConversationLabelEnum.UNVERIFIED.getLabel());
        }
        ChatwootAddLabelRequest chatwootAddLabelRequest = new ChatwootAddLabelRequest();
        chatwootAddLabelRequest.setContactId(hotelGuest.getChatwootContactId());
        chatwootAddLabelRequest.setLabels(labels);
        Boolean addLabelResult = chatwootLabelService.addLabel(chatwootAddLabelRequest);
        if (!addLabelResult) {
            logger.error("chatwoot 添加label失败，guestId:{} 添加label失败", request.getGuestId());
            return ResponseEntity.ok().body(ResponseResult.fail("chatwoot 添加label失败， guestId:" + request.getGuestId()));
        }
        hotelGuest.setVerify(request.getVerify());
        hotelGuest.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        hotelGuestRepository.save(hotelGuest);
        return ResponseEntity.ok(hotelGuest);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteGuest(HotelGuestDeleteRequest request) {
        if (!hotelGuestRepository.existsById(request.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guest not found");
        }
        hotelGuestRepository.deleteById(request.getId());
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> getGuestById(HotelGuestDetailRequest request) {
        HotelGuest hotelGuest = hotelGuestRepository.findById(request.getId()).orElse(null);
        if (hotelGuest == null) {
            return ResponseEntity.ok().body(ResponseResult.fail("Guest not found"));
        }
        return ResponseEntity.ok(hotelGuest);
    }

    // 判空获取sourceId
    private String safeGetSourceId(ChatwootContactDetailBO detailBO) {
        if (detailBO == null || detailBO.getContactInboxes() == null || detailBO.getContactInboxes().isEmpty()) {
            return null;
        }
        ChatwootContactInboxBO inbox = detailBO.getContactInboxes().get(0);
        return inbox != null ? inbox.getSourceId() : null;
    }
}