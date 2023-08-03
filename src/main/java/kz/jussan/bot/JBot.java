package kz.jussan.bot;

import kz.jussan.bot.config.HelpConfig;
import kz.jussan.bot.config.TelegramConfig;
import kz.jussan.bot.model.Location;
import kz.jussan.bot.model.Temperature;
import kz.jussan.bot.repository.HelpRequest;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JBot extends TelegramLongPollingBot {
    public static List<Location> locations;
    public static List<Temperature> temperatures;


    static {
        locations = new ArrayList<>();
        locations.add(Location.A1);
        locations.add(Location.A2);
        locations.add(Location.A3);

        temperatures = new ArrayList<>();
        temperatures.add(Temperature.COLD);
        temperatures.add(Temperature.WARM);
    }

    public JBot(String token) {
        super(token);
    }
    //TODO запилить меню в отдельном пакете
    //TODO хасинхронить его с хелп оно и будет меню
    //TODO рефакторинг!!

    public String getBotUsername() {
        return TelegramConfig.NAME;
    }

    public void onRegister() {
        super.onRegister();
    }

    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            String query = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            System.out.println(chatId + ": " + query);
            switch (query) {
                case "COLD" -> lowTemperatureCommandReceived(chatId);
                case "WARM" -> highTemperatureCommandReceived(chatId);
                default -> sendMessage(chatId, "Idk what is it", null);
            }
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            System.out.println(chatId + ": " + message);

            switch (message) {
                case "/start" -> startCommandReceived(chatId);
                case "/clim" -> showClimateMenu(chatId);
                case "/help" -> showMainMenu(chatId);
                default -> sendMessage(chatId, "Idk what is it", null);
            }
        }
    }

    private void startCommandReceived(long chatId) {
        showMainMenu(chatId);
    }

    private void showMainMenu(long chatId) {
        String answer = "Hey hey, Welcome \n" +
                "We glad to see you \n" +
                "My functions:";
        answer = answer + "\n" + HelpConfig.getHelpMsg();
        SendMessage sm = SendMessage.builder().chatId(chatId).text(answer).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void showClimateMenu(long chatId) {
        String answer = "If you want to change temperature in office click buttons";
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        for (Temperature loc : temperatures) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(loc.description());
            button.setCallbackData(loc.name());
            row.add(button);
        }
        keyboard.add(row);
        sendMessage(chatId, answer, keyboard);

    }

    private void lowTemperatureCommandReceived(long chatId) {
        String answer = "Select zone";

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        locations.forEach(loc -> {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(loc.description());
            button.setCallbackData(loc.name());
            row.add(button);
        });
        keyboard.add(row);
        sendMessage(chatId, answer, keyboard);
        sendPhoto(chatId);
    }

    private void highTemperatureCommandReceived(long chatId) {
        String answer = "Select zone";

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();
        locations.forEach(loc -> {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(loc.description());
            button.setCallbackData(loc.name());
            row.add(button);
        });
        keyboard.add(row);

        sendMessage(chatId, answer, keyboard);
        sendPhoto(chatId);
    }

    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    private void sendMessage(long chatId, String textTosSend, List<List<InlineKeyboardButton>> buttons) {
        SendMessage message = new SendMessage(String.valueOf(chatId), textTosSend);
        if (buttons != null && !buttons.isEmpty()) {
            // Create ReplyKeyboardMarkup object
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            // Set the keyboard to the markup
            keyboardMarkup.setKeyboard(buttons);
            // Add it to the message
            message.setReplyMarkup(keyboardMarkup);
        }
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendPhoto(long chatId) {
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource("map.jpg")).getPath();

        SendPhoto message = new SendPhoto(String.valueOf(chatId),
                new InputFile(new File(filePath)));
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
