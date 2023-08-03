package kz.jussan.bot;

import kz.jussan.bot.config.HelpConfig;
import kz.jussan.bot.config.TelegramConfig;
import kz.jussan.bot.repository.HelpRequest;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            HelpConfig.loadConfig("./help.json");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new JBot(TelegramConfig.TOKEN));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}