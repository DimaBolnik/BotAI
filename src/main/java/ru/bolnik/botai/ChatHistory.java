package ru.bolnik.botai;

import lombok.Builder;
import lombok.Data;
import ru.bolnik.botai.api.Message;

import java.util.List;
@Data
@Builder
public class ChatHistory {

    private List<Message> chatMessages;
}
