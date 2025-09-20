package com.example.hotelmanagement.service.ai;

import com.example.hotelmanagement.enums.LanguageEnum;
import com.example.hotelmanagement.model.dto.MessageContentInfo;
import com.example.hotelmanagement.model.dto.TranslateResultInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.hotelmanagement.model.bo.OpenAiResponseBO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class AiTranslateService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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";


    public List<TranslateResultInfo> batchTranslate(List<MessageContentInfo> messages, LanguageEnum language) {
        if (CollectionUtils.isEmpty(messages)) {
            return Collections.emptyList();
        }
        String jsonInput = gson.toJson(messages);
        String prompt = buildPrompt(language.getDescription());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-5-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", prompt),
                        Map.of("role", "user", "content", jsonInput)
                )
        );

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(requestBody), headers);

        // Log the cURL command for debugging
        String requestBodyJson = gson.toJson(requestBody);
        String escapedData = requestBodyJson.replace("'", "'\\''"); // Escape single quotes for shell safety
        String curl = String.format(
                "curl -X POST '%s' \\\n-H 'Authorization: Bearer %s' \\\n-H 'Content-Type: application/json' \\\n-d '%s'",
                OPENAI_API_URL,
                API_KEY,
                escapedData
        );
        System.out.println("\n--- cURL for OpenAI Request ---\n" + curl + "\n---------------------------------\n");

        String responseJson = restTemplate.postForObject(OPENAI_API_URL, request, String.class);
        OpenAiResponseBO response = gson.fromJson(responseJson, OpenAiResponseBO.class);

        if (response != null && response.getChoices() != null && !response.getChoices().isEmpty()) {
            String content = response.getChoices().get(0).getMessage().getContent();
            if (!StringUtils.hasText(content)) {
                return Collections.emptyList();
            }
            return gson.fromJson(content, new TypeToken<List<TranslateResultInfo>>() {}.getType());
        }
        return Collections.emptyList();
    }

    private String buildPrompt(String targetLanguage) {
        return String.format(
                "### ROLE & GOAL ###\n" +
                        "You are an expert linguist and a professional translation engine. Your primary goal is to provide accurate, natural-sounding translations for the JSON data provided by the user.\n\n" +

                        "### TARGET LANGUAGE ###\n" +
                        "The target language for this translation task is: **%s**\n\n" +

                        "### INSTRUCTIONS ###\n" +
                        "1.  You will receive a JSON array of objects, where each object contains a 'messageId' and a 'content' field.\n" +
                        "2.  Translate the 'content' field of each object into the specified **TARGET LANGUAGE (%s)**.\n" +
                        "3.  The 'messageId' serves as a unique identifier and **must not** be altered in any way.\n" +
                        "4.  Your response **must** be a valid JSON array of objects.\n" +
                        "5.  Each object in your response must contain the original 'messageId' and the translated text in a new field named 'result'.\n\n" +

                        "### EXAMPLE ###\n" +
                        "If the target language is French and the input is:\n" +
                        "`[{\"messageId\":1,\"content\":\"Hello, how are you?\"}, {\"messageId\":2,\"content\":\"Good morning.\"}]`\n\n" +
                        "Your response should be:\n" +
                        "`[{\"messageId\":1,\"result\":\"Bonjour, comment ça va ?\"}, {\"messageId\":2,\"result\":\"Bonjour.\"}]`\n\n" +

                        "### IMPORTANT ###\n" +
                        "- Ensure the output is a single, minified JSON array without any additional text, explanations, or formatting.\n" +
                        "- Preserve the original meaning and tone of the content as much as possible.\n" +
                        "- Do not translate any JSON keys ('messageId', 'content', 'result').",
                targetLanguage, targetLanguage);
    }
}