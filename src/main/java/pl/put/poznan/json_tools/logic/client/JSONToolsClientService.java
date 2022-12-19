package pl.put.poznan.json_tools.logic.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JSONToolsClientService {

    private final WebClient webClient;

    public JSONToolsClientService(WebClient.Builder builder) {
        webClient = builder.baseUrl("http://localhost:8080/json-tools").build();
    }

    public String getMinify(String JSONString) {
        String response = webClient
                .post()
                .uri("/minify")
                .bodyValue(JSONString)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}
