package smartinvestor.dataingestion.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import smartinvestor.dataingestion.dto.FredResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class FredClient {

//    private final WebClient.Builder webClientBuilder;
    private final WebClient fredWebClient;

    @Value("${fred.base-url}")
    private String baseUrl;

    @Value("${fred.api-key}")
    private String apiKey;

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    public FredResponse getSeries(String seriesId) {
        log.info("Fetching FRED series {}", seriesId);

        return fredWebClient //.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fred/series/observations")
                        .queryParam("series_id", seriesId)
                        .queryParam("api_key", apiKey)
                        .queryParam("file_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(FredResponse.class)
                .block();
    }
}