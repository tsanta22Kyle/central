package org.accdatabase.central.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor@Getter@Setter@NoArgsConstructor
public class SaleRest {
    private String dishOrderId;
    private String dishId;
    private String dishName;
    private int quantitySold;
    private List<OrderStatus> statusHistory = new ArrayList<OrderStatus>();
}
