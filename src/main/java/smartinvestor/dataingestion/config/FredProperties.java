package smartinvestor.dataingestion.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "fred")
public class FredProperties {

    @NotBlank
    private String baseUrl;

    @NotBlank
    private String apiKey;

    private int connectTimeoutMs;

    private int readTimeoutMs;
}