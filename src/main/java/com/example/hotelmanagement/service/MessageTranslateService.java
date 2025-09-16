package com.example.hotelmanagement.service;

import org.springframework.http.ResponseEntity;

import com.example.hotelmanagement.model.request.GetTranslateResultRequest;

public interface MessageTranslateService {
    ResponseEntity<?> getTranslateResult(GetTranslateResultRequest request);
}


