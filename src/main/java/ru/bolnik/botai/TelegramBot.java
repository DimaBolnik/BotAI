package ru.bolnik.botai;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bolnik.botai.openai.ChatGptService;


public class TelegramBot extends TelegramLongPollingBot {

    private final ChatGptService chatGptService;


    public TelegramBot(DefaultBotOptions options, String botToken, ChatGptService chatGptService) {
        super(options, botToken);
        this.chatGptService = chatGptService;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            String responseChatUser = chatGptService.getResponseChatUser(chatId, text);

            // Разбиваем и отправляем ответ частями
            sendSplitMessage(chatId.toString(), responseChatUser);
        }
    }

    /**
     * Отправляет длинное сообщение, разбивая его на части по 4096 символов.
     */
    private void sendSplitMessage(String chatId, String messageText) {
        final int maxLength = 4096;

        // Если сообщение короче лимита — просто отправляем его
        if (messageText.length() <= maxLength) {
            sendMessage(chatId, messageText);
            return;
        }

        // Иначе разбиваем на части
        int start = 0;
        while (start < messageText.length()) {
            int end = Math.min(messageText.length(), start + maxLength);
            String part = messageText.substring(start, end);
            sendMessage(chatId, part);
            start = end;
        }
    }

    /**
     * Отправляет одно сообщение пользователю
     */
    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        try {
            execute(message); // Можно использовать executeAsync() для асинхронной отправки
        } catch (TelegramApiException e) {
            e.printStackTrace(); // Лучше заменить на нормальную логгирование
        }
    }

    @Override
    public String getBotUsername() {
        return "MyTEstBot";
    }


}
