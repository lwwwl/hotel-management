package com.example.hotelmanagement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotelmanagement.aop.annotation.RequireUserId;
import com.example.hotelmanagement.model.request.GetTranslateResultRequest;
import com.example.hotelmanagement.service.MessageTranslateService;

import jakarta.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping("/translate")
public class MessageTranslateController {

    @Resource
    private MessageTranslateService messageTranslateService;

    /**
     * 获取会话所有消息的翻译结果
     */
    @PostMapping("/get-translate-result")
    public ResponseEntity<?> getTranslateResult(@RequestBody GetTranslateResultRequest request) {
        return messageTranslateService.getTranslateResult(request);
    }
}


