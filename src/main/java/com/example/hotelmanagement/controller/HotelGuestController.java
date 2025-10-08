package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireGuestId;
import com.example.hotelmanagement.model.request.*;
import com.example.hotelmanagement.service.ChatwootGuestFacadeService;
import com.example.hotelmanagement.service.HotelGuestService;
import com.example.hotelmanagement.service.HotelQuickMenuService;
import com.example.hotelmanagement.service.MessageTranslateService;
import com.example.hotelmanagement.util.GuestContext;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 此controller实现客人端会请求的接口，nginx层面拦截/api/v1/guest/可以不经过authelia鉴权。
 */

@RestController
@CrossOrigin
@RequestMapping("/chat-guest")
public class HotelGuestController {

    @Resource
    private ChatwootGuestFacadeService chatwootGuestFacadeService;

    @Resource
    private HotelGuestService hotelGuestService;

    @Resource
    private MessageTranslateService messageTranslateService;

    @Resource
    private HotelQuickMenuService quickMenuService;

    @RequireGuestId
    @PostMapping("/create-message")
    public ResponseEntity<?> createMessage(@RequestBody ChatwootGuestCreateMessage chatwootGuestCreateMessage) {
        return chatwootGuestFacadeService.sendMessage(chatwootGuestCreateMessage.getContent(), GuestContext.getGuestId());
    }

    @RequireGuestId
    @PostMapping("/message-list")
    ResponseEntity<?> getMessages() {
        return  chatwootGuestFacadeService.getMessages(GuestContext.getGuestId());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createGuest(@RequestBody HotelGuestCreateRequest request) {
        return hotelGuestService.createGuest(request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateGuest(@RequestBody HotelGuestUpdateRequest request) {
        return hotelGuestService.updateGuest(request);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteGuest(@RequestBody HotelGuestDeleteRequest request) {
        return hotelGuestService.deleteGuest(request);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getGuestById(@RequestBody HotelGuestDetailRequest request) {
        return hotelGuestService.getGuestById(request);
    }

    @PostMapping("/translate/get-translate-result")
    public ResponseEntity<?> getTranslateResult(@RequestBody GetTranslateResultRequest request) {
        return messageTranslateService.getTranslateResult(request);
    }

    /** 列表，返回全量快捷菜单 */
    @PostMapping("/quick-menu/list")
    public ResponseEntity<?> list(@RequestBody QuickMenuListRequest request) {
        return quickMenuService.list(request);
    }
}
