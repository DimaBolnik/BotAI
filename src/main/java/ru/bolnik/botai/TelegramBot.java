package ru.bolnik.botai;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    public TelegramBot(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            SendMessage message = new SendMessage(chatId.toString(),text);
            sendApiMethod(message);
        }

    }

    @Override
    public String getBotUsername() {
        return "MyTEstBot";
    }


}
