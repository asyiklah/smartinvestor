package smartinvestor.dataingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;
import smartinvestor.dataingestion.config.FredProperties;

@SpringBootApplication
@EnableRetry
@EnableConfigurationProperties(FredProperties.class)
public class DataingestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataingestionApplication.class, args);
	}
}
