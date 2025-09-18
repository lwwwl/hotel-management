package com.example.hotelmanagement.service.ai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiTranslateService {

    private final OpenAIClient openAIClient;

    // 从 application.properties 或 application.yml 读取 API Key
    public AiTranslateService(@Value("${openai.api.key}") String apiKey) {
        this.openAIClient = new OpenAIOkHttpClient(apiKey);
    }

    /**
     * 调用 AI 翻译消息
     * @param targetLang 目标语言（中文描述，如 "英文"、"日文"）
     * @param messagesJson 消息列表 JSON（格式：[{"messageId":1,"content":"你好"}]）
     * @return 翻译结果 JSON（格式保持不变，只翻译 content 字段）
     */
    public String translateMessages(String targetLang, String messagesJson) {
        String systemPrompt = "你是一个专业的翻译助手。你的任务是将输入的消息内容翻译成指定语言，并严格保持输入的结构化格式返回。\n" +
                "要求：\n" +
                "1. 保留每条消息的 messageId，不得修改。\n" +
                "2. 仅翻译 content 字段的文本，不要改动其他字段。\n" +
                "3. 返回结果必须是 JSON 数组，结构与输入完全一致，只是 content 替换为翻译后的文本。\n" +
                "4. 不要输出除 JSON 之外的任何多余说明或注释。";

        String userPrompt = "目标语言：" + targetLang + "\n\n" +
                "输入消息列表：\n" + messagesJson + "\n\n" +
                "请将以上消息翻译成目标语言，并返回格式化结果。";

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1)
                .messages(List.of(
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), systemPrompt),
                        new ChatMessage(ChatMessageRole.USER.value(), userPrompt)
                ))
                .build();

        ChatCompletion chatCompletion = openAIClient.chat().create(params);
        return chatCompletion.getChoices().get(0).getMessage().getContent().trim();
    }
}