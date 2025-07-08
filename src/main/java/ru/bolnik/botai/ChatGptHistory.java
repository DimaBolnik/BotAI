package ru.bolnik.botai;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bolnik.botai.api.Message;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class ChatGptHistory {

    private final Map<Long, ChatHistory> chatHistoryMap = new ConcurrentHashMap<>();

    public Optional<ChatHistory> getUserHistory(Long chatId) {
        return Optional.ofNullable(chatHistoryMap.get(chatId));
    }

    public void createHistory(Long chatId) {
        chatHistoryMap.put(chatId, new ChatHistory(new ArrayList<>()));
    }

    public ChatHistory addMessageToHistory(Long chatId, Message message) {
        ChatHistory chatHistory = chatHistoryMap.computeIfAbsent(chatId, k -> new ChatHistory(new ArrayList<>()));
        chatHistory.getChatMessages().add(message);
        return chatHistory;
    }
}
