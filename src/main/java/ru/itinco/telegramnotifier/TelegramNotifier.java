package ru.itinco.telegramnotifier;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TelegramNotifier {

    private String message="";

    private String chatId;
    private String token;

    /**
     * Simple send message to telegram user
     *
     * @return Status code of delivery
     * @throws IOException drop if can't to send
     * @throws InterruptedException canceled by interrupted
     */
    public int sendMessage() throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", chatId)
                .queryParam("text", message);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + token))
                .timeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode();

    }

    public static class Builder{
        private final TelegramNotifier notifier;

        public Builder(){
            notifier=new TelegramNotifier();
        }
        public Builder withToken(String token){
            notifier.token =token;
            return this;
        }
        public Builder withChatId(String chatId){
            notifier.chatId =chatId;
            return this;
        }
        public Builder withMessage(String message){
            notifier.message=message;
            return this;
        }
        public TelegramNotifier build(){
            return notifier;
        }
    }
}
