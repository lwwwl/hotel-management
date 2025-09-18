package com.example.hotelmanagement.model.request;

import java.util.List;

import com.example.hotelmanagement.enums.LanguageEnum;
import lombok.Data;

@Data
public class GetTranslateResultRequest {
    private Long conversationId;
    private List<Long> messageIdList;
    private LanguageEnum language;
}


