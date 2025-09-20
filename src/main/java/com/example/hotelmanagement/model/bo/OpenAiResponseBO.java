package com.example.hotelmanagement.model.bo;

import lombok.Data;
import java.util.List;

@Data
public class OpenAiResponseBO {
    private List<OpenAiChoiceBO> choices;
}
