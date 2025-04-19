package org.accdatabase.central.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class BestSales {
    private LocalDateTime updatedAt;
    private List<DishSold> dishSolds;
}
