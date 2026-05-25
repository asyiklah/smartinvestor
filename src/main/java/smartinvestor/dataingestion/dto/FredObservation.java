package smartinvestor.dataingestion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FredObservation {
    private String date;
    private String value;
}