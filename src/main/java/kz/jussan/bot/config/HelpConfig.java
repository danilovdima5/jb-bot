package kz.jussan.bot.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelpConfig {
    private static String helpText;

    public static void loadConfig(String configFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,String> commandsMap;
        try {
            commandsMap = mapper.readValue(new File(configFilePath), HashMap.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        StringBuilder textBuilder = new StringBuilder();
        for (Map.Entry<String, String> pair : commandsMap.entrySet()) {
            textBuilder.append(pair.getKey());
            textBuilder.append(" - ");
            textBuilder.append(pair.getValue());
            textBuilder.append("\n");
        }
        helpText = textBuilder.toString();
    }

    public static String getHelpMsg() {
        return helpText;
    }
}
