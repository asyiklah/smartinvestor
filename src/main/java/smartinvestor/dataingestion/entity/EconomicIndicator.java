package smartinvestor.dataingestion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "economic_indicator",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "series_id",
                                "observation_date"
                        }
                )
        },
        indexes = {
                @Index(name = "idx_series_id", columnList = "series_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EconomicIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "indicator_name", nullable = false)
    private String indicatorName;

    @Column(name = "series_id", nullable = false)
    private String seriesId;

    @Column(nullable = false)
    private Double value;

    @Column(name = "observation_date", nullable = false)
    private LocalDate observationDate;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private OffsetDateTime createdAt;
}