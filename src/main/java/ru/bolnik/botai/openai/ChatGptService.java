package ru.bolnik.botai.openai;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.bolnik.botai.ChatGptHistory;
import ru.bolnik.botai.ChatHistory;
import ru.bolnik.botai.api.ChatCompletionRequest;
import ru.bolnik.botai.api.ChatCompletionResponse;
import ru.bolnik.botai.api.Message;
import ru.bolnik.botai.api.OpenAiClient;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    private final OpenAiClient openAiClient;
    private final ChatGptHistory chatGptHistory;
    @Value("${bot.model}")
    private String model;

    @NonNull
    public String getResponseChatUser(Long userId, String userText) {

        ChatHistory history = chatGptHistory.addMessageToHistory(userId, Message.builder()
                .content(userText)
                .role("user")
                .build());


        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model)
                .messages(history.getChatMessages())
                .build();
        ChatCompletionResponse response = openAiClient.createChatCompletion(request);

        Message message = response.getChoices().get(0).getMessage();
        chatGptHistory.addMessageToHistory(userId, message);
        return message.getContent();

    }
}
