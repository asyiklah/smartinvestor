package smartinvestor.dataingestion.dto;

import lombok.Data;

import java.util.List;

@Data
public class FredResponse {
    private List<FredObservation> observations;
}