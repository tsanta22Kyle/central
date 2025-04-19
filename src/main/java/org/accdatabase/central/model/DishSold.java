package org.accdatabase.central.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class DishSold {
    private String salesPoint;
    private Dish dish;
    private Double quantitySold;
    private Double totalAmount;
}
