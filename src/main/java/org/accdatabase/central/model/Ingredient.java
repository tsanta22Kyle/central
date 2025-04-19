package org.accdatabase.central.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.dd_lgp.com.tutospring.model.StockMovementType.IN;
import static org.dd_lgp.com.tutospring.model.StockMovementType.OUT;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString

public class Ingredient {
    private Long id;
    private String name;
    @JsonIgnore
    private List<Price> prices;
    @JsonIgnore
    private List<StockMovement> stockMovements;

    public List<StockMovement> addStockMovements(List<StockMovement> stockMovements) {
        stockMovements.forEach(stockMovement -> stockMovement.setIngredient(this));
        if (getStockMovements() == null || getStockMovements().isEmpty()) {
            return stockMovements;
        }
        getStockMovements().addAll(stockMovements);
        return getStockMovements();
    }

    public List<Price> addPrices(List<Price> prices) {
        if (getPrices() == null || getPrices().isEmpty()) {
            return prices;
        }
        prices.forEach(price -> price.setIngredient(this));
        getPrices().addAll(prices);
        return getPrices();
    }

    public Double getActualPrice() {
        return findActualPrice().orElse(new Price(0.0)).getAmount();
    }

    public Double getAvailableQuantity() {
        return getAvailableQuantityAt(Instant.now());
    }

    public Double getPriceAt(LocalDate dateValue) {
        return findPriceAt(dateValue).orElse(new Price(0.0)).getAmount();
    }

    public Double getAvailableQuantityAt(Instant datetime) {
        List<StockMovement> stockMovementsBeforeToday = stockMovements.stream()
                .filter(stockMovement ->
                        stockMovement.getCreationDatetime().isBefore(datetime)
                                || stockMovement.getCreationDatetime().equals(datetime))
                .toList();
        double quantity = 0.0;
        for (StockMovement stockMovement : stockMovementsBeforeToday) {
            if (IN.equals(stockMovement.getMovementType())) {
                quantity += stockMovement.getQuantity();
            } else if (OUT.equals(stockMovement.getMovementType())) {
                quantity -= stockMovement.getQuantity();
            }
        }
        return quantity;
    }

    private Optional<Price> findPriceAt(LocalDate dateValue) {
        return prices.stream()
                .filter(price -> price.getDateValue().equals(dateValue))
                .findFirst();
    }

    private Optional<Price> findActualPrice() {
        return prices.stream().max(Comparator.comparing(Price::getDateValue));
    }
}
