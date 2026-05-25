package smartinvestor.dataingestion.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import smartinvestor.dataingestion.service.EconomicDataService;

@Component
@RequiredArgsConstructor
@Slf4j
public class EconomicIndicatorScheduler {

    private final EconomicDataService service;

    @Scheduled(cron = "0 0 6 * * *", zone = "UTC")
    public void fetchEconomicIndicators() {

        log.info("Starting economic data ingestion");

        service.fetchAndStore("CPIAUCSL", "US CPI");
        service.fetchAndStore("FEDFUNDS", "Fed Funds Rate");
        service.fetchAndStore("UNRATE", "US Unemployment");
        service.fetchAndStore("DGS10", "10Y Treasury");
        service.fetchAndStore("DGS2", "2Y Treasury");

        log.info("Completed economic data ingestion");
    }
}