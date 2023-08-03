package kz.jussan.bot;

import kz.jussan.bot.config.TelegramConfig;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.ObjectInputFilter;
import java.util.List;

public class JBot extends TelegramLongPollingBot {
    public String getBotUsername() {
        return TelegramConfig.NAME;
    }

    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotToken() {
        return TelegramConfig.TOKEN;
    }

    public void onUpdateReceived(Update update) {
        System.out.println(update);
    }

    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }
}