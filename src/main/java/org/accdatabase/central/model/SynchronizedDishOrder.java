package org.accdatabase.central.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor@NoArgsConstructor@Getter@Setter
public class SynchronizedDishOrder {
    private String DishOrderId;
    private String saleId;
    private String DishId;
    private Integer Quantity;
    private String DishName;
    @JsonIgnore
    private List<SynchronizedStatusHistory> OrderStatus =  new ArrayList<SynchronizedStatusHistory>();
    private LocalDateTime syncDate;


}
