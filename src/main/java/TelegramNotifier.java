import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class TelegramNotifier {

    private String message="";

    private String CHAT_ID;
    private String TOKEN;

    /**
     * Simple send message to telegram user
     *
     * @return Status code of delivery
     * @throws IOException drop if can't to send
     * @throws InterruptedException canceled by interrupted
     */
    public int sendMessage() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.statusCode();

    }

    public static class Builder{
        private final TelegramNotifier notifier;

        public Builder(){
            notifier=new TelegramNotifier();
        }
        public Builder withToken(String token){
            notifier.TOKEN=token;
            return this;
        }
        public Builder withChatId(String chatId){
            notifier.CHAT_ID=chatId;
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
