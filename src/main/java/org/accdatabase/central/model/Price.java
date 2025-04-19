package org.accdatabase.central.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString

public class Price {
    private Long id;
    @JsonIgnore
    private Ingredient ingredient;
    private Double amount;
    private LocalDate dateValue;

    public Price(Double amount) {
        this.amount = amount;
        this.dateValue = now();
    }
}
