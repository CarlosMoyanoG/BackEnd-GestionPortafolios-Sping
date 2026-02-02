package ec.edu.ups.ppw.GestorProyectos.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramClient {

    @Value("${telegram.api.url}")
    private String apiUrl;

    @Value("${telegram.internal.token}")
    private String internalToken;

    private final RestTemplate rest = new RestTemplate();

    @Value("${telegram.chat.id}")
    private String defaultChatId;

    public void send(String chatId, String message) {
        String finalChatId = (chatId == null || chatId.isBlank()) ? defaultChatId : chatId;
        if (finalChatId == null || finalChatId.isBlank()) return;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Internal-Token", internalToken);

        Map<String, String> body = Map.of(
            "chat_id", finalChatId,
            "message", message
        );

        HttpEntity<Map<String, String>> req = new HttpEntity<>(body, headers);
        rest.postForEntity(apiUrl + "/api/telegram/send", req, String.class);
    }
}
