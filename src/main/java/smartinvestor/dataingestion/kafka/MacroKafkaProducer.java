package smartinvestor.dataingestion.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import smartinvestor.dataingestion.event.EconomicIndicatorEvent;

@Service
@RequiredArgsConstructor
public class MacroKafkaProducer {

    private final KafkaTemplate<String, EconomicIndicatorEvent> kafkaTemplate;

    public void publish(EconomicIndicatorEvent event) {
        kafkaTemplate.send("economic-indicators", event.getSeriesId(), event);
    }
}