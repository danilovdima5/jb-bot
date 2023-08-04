package kz.jussan.bot.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import kz.jussan.bot.config.GPTConfig;

import java.util.ArrayList;
import java.util.List;

import static kz.jussan.bot.config.GPTConfig.GPT_TOKEN;
import static kz.jussan.bot.config.GPTConfig.MASER_ACTOR;

public class GptService {

    private final OpenAiService openAI;

    public GptService() {
        openAI = new OpenAiService(GPT_TOKEN);
    }


    public String haughtyBot(String mess) {
        List<ChatMessage> messages = new ArrayList<>();

        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), MASER_ACTOR);
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), mess);
        messages.add(systemMessage);
        messages.add(userMessage);
        ChatCompletionRequest ccr = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .maxTokens(300)
                .temperature(0.8)
                .build();

        ChatCompletionResult res = openAI.createChatCompletion(ccr);
        return res.getChoices().get(0).getMessage().getContent();
    }

}
