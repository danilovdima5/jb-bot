package kz.jussan.bot.whatsapp;

import kz.jussan.bot.config.WhatsappConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {


    public static void main(String[] args) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(WhatsappConfig.MESSAGE_URI))
                    .header("Authorization", "Bearer " + WhatsappConfig.TOKEN)
                    .header("Content-Type", "application/json")
                    //TODO сделать шаблон
                    .POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\": \"787778080085\", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }"))
                    .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
