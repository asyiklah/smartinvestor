package smartinvestor.dataingestion.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import smartinvestor.dataingestion.client.FredClient;
import smartinvestor.dataingestion.dto.FredObservation;
import smartinvestor.dataingestion.dto.FredResponse;
import smartinvestor.dataingestion.entity.EconomicIndicator;
import smartinvestor.dataingestion.event.EconomicIndicatorEvent;
import smartinvestor.dataingestion.kafka.MacroKafkaProducer;
import smartinvestor.dataingestion.repository.EconomicIndicatorRepository;

import java.time.LocalDate;
import java.util.Comparator;

@Slf4j
@Service
@RequiredArgsConstructor
public class EconomicDataService {

    private final FredClient fredClient;
    private final EconomicIndicatorRepository repository;
    private final MacroKafkaProducer kafkaProducer;

    public void fetchAndStore(String seriesId, String indicatorName) {

        FredResponse response = fredClient.getSeries(seriesId);
        if (response == null || response.getObservations().isEmpty()) {
            log.info("Fred response is empty/null");
            return;
        }

//        FredObservation latest =
//                response.getObservations()
//                        .get(response.getObservations().size() - 1);
//        if (".".equals(latest.getValue())) {
//            return;
//        }
        FredObservation latest =
                response.getObservations()
                        .stream()
                        .filter(o -> !".".equals(o.getValue()))
                        .max(Comparator.comparing(FredObservation::getDate))
                        .orElseThrow();

        LocalDate observationDate = LocalDate.parse(latest.getDate());

        if (repository.existsBySeriesIdAndObservationDate(seriesId, observationDate)) {
            log.info("Indicator already exists {} {}", seriesId, observationDate);
            return;
        }

        EconomicIndicator indicator =
                EconomicIndicator.builder()
                        .indicatorName(indicatorName)
                        .seriesId(seriesId)
                        .value(Double.parseDouble(latest.getValue()))
                        .observationDate(LocalDate.parse(latest.getDate()))
                        .source("FRED")
                        .build();

        repository.save(indicator);

        kafkaProducer.publish(EconomicIndicatorEvent.builder()
                .indicatorName(indicatorName)
                .seriesId(seriesId)
                .value(indicator.getValue())
                .observationDate(observationDate)
                .build()
        );

        log.info("Stored indicator {} {}", seriesId, indicator.getValue());
    }
}