package kz.jussan.bot.whatsapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App {

    private final String token = "EAAMScZB7gPSkBO1Wm2eYE8SZBc2IFn8EQQWqX88aDRWDYJshTR64c3Rv80PwkX2YUAvcyk0VsUE6wgVkJRl7kYXNUrNID0EZBZCgT2lws6MWN4lqZAZAvbxKMiPc8infKnZCgsG0EmG9E8RuALvJLi7vl0qMZAkBmvOgEKvxrgHaWWpTjl0iXM07cPzWlmyzGuZB417wEyWiKIqTWPMUZD";

    private final String appId = "112727178578230";
    public static void main( String[] args )
    {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://graph.facebook.com/v13.0/112727178578230/messages"))
                    .header("Authorization", "Bearer EAAMScZB7gPSkBO1Wm2eYE8SZBc2IFn8EQQWqX88aDRWDYJshTR64c3Rv80PwkX2YUAvcyk0VsUE6wgVkJRl7kYXNUrNID0EZBZCgT2lws6MWN4lqZAZAvbxKMiPc8infKnZCgsG0EmG9E8RuALvJLi7vl0qMZAkBmvOgEKvxrgHaWWpTjl0iXM07cPzWlmyzGuZB417wEyWiKIqTWPMUZD")
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
