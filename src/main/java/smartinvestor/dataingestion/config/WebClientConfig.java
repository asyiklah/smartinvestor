package smartinvestor.dataingestion.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final FredProperties properties;

    @Bean
    public WebClient fredWebClient() {

        HttpClient httpClient = HttpClient.create()
                .option(
                        ChannelOption.CONNECT_TIMEOUT_MILLIS,
                        properties.getConnectTimeoutMs()
                )
                .doOnConnected(conn ->
                        conn.addHandlerLast(
                                new ReadTimeoutHandler(
                                        properties.getReadTimeoutMs(),
                                        TimeUnit.MILLISECONDS
                                )
                        ));

        return WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}