package smartinvestor.dataingestion.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EconomicIndicatorEvent {

    private String indicatorName;

    private String seriesId;

    private Double value;

    private LocalDate observationDate;
}