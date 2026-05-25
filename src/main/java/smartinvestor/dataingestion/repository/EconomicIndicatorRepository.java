package smartinvestor.dataingestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smartinvestor.dataingestion.entity.EconomicIndicator;

import java.time.LocalDate;
import java.util.Optional;

public interface EconomicIndicatorRepository extends JpaRepository<EconomicIndicator, Long> {

    boolean existsBySeriesIdAndObservationDate(String seriesId, LocalDate observationDate);

    Optional<EconomicIndicator> findTopBySeriesIdOrderByObservationDateDesc(String seriesId);
}