package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireGuestId;
import com.example.hotelmanagement.model.request.ChatwootGuestCreateMessage;
import com.example.hotelmanagement.service.ChatwootGuestFacadeService;
import com.example.hotelmanagement.util.GuestContext;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireGuestId
@CrossOrigin
@RequestMapping("/chat-guest")
public class ChatwootGuestController {

    @Resource
    private ChatwootGuestFacadeService chatwootGuestFacadeService;

    @PostMapping("/create-message")
    public ResponseEntity<?> createMessage(@RequestBody ChatwootGuestCreateMessage chatwootGuestCreateMessage) {
        return chatwootGuestFacadeService.sendMessage(chatwootGuestCreateMessage.getContent(), GuestContext.getGuestId());
    }

    @PostMapping("/message-list")
    ResponseEntity<?> getMessages() {
        return  chatwootGuestFacadeService.getMessages(GuestContext.getGuestId());
    }
}
