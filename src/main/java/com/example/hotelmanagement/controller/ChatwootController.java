package com.example.hotelmanagement.controller;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootAddNewAgentRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDeleteRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserDetailRequest;
import com.example.hotelmanagement.model.request.chatwoot.ChatwootUserUpdateRequest;
import com.example.hotelmanagement.service.ChatwootFacadeService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequireUserId
@CrossOrigin
@RequestMapping("/chat-user")
public class ChatwootController {

    @Resource
    private ChatwootFacadeService chatwootFacadeService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody ChatwootAddNewAgentRequest request) {
        return chatwootFacadeService.createUser(request);
    }

    @PostMapping("/detail")
    public ResponseEntity<?> getUserDetail(@RequestBody ChatwootUserDetailRequest request) {
        return chatwootFacadeService.getUserDetail(request);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody ChatwootUserUpdateRequest request) {
        return chatwootFacadeService.updateUser(request);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody ChatwootUserDeleteRequest request) {
        return chatwootFacadeService.deleteUser(request);
    }
}
