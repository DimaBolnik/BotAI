package ru.bolnik.botai.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.bolnik.botai.TelegramBot;
import ru.bolnik.botai.openai.ChatGptService;

@Configuration
public class TelegramConfig {


    @Bean
    @SneakyThrows
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    @SneakyThrows
    public TelegramBot telegramBot(@Value("${bot.token}") String token,
                                   TelegramBotsApi api,
                                   ChatGptService gptService) {

        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
        TelegramBot bot = new TelegramBot(defaultBotOptions, token, gptService);
        api.registerBot(bot);
        return bot;
    }
}
