package org.accdatabase.central.service;

import lombok.RequiredArgsConstructor;
import org.accdatabase.central.dao.operations.PointOfSaleOperations;
import org.accdatabase.central.dao.operations.SynchronizedDishOrderOperations;
import org.accdatabase.central.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SynchronizationService {


        @Autowired
        private PointOfSaleOperations pointOfSaleOperations;
        @Autowired
        private SynchronizedDishOrderOperations syncDishOrderOperations;

        private final RestTemplate restTemplate = new RestTemplate();


    public List<SynchronizedDishOrder> syncProcessingTime() throws SQLException {
        List<PointOfSale> pointOfSales = pointOfSaleOperations.findAll();
        List<SaleRest> saleRests = new ArrayList<>();

        pointOfSales.forEach(pointOfSale -> {
            ResponseEntity<List<SaleRest>> response = restTemplate.exchange(
                    pointOfSale.getApiUrl() + "orders/sales",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<SaleRest>>() {}
            );
            saleRests.addAll(response.getBody());
        });

        return syncDishOrderOperations.saveAll(saleRests.stream()
                .map(this::mapToSynchronizedDishOrder)
                .toList());
    }

    private SynchronizedDishOrder mapToSynchronizedDishOrder(SaleRest saleRest) {
        SynchronizedDishOrder order = new SynchronizedDishOrder();
        order.setDishId(saleRest.getDishId());
        order.setDishName(saleRest.getDishName());
        order.setDishOrderId(saleRest.getDishOrderId());
        order.setQuantity(saleRest.getQuantitySold());
        order.setOrderStatus(mapToSynchronizedStatusHistories(saleRest.getStatusHistory()));
        return order;
    }

    private List<SynchronizedStatusHistory> mapToSynchronizedStatusHistories(List<OrderStatus> statusHistories) {
        return statusHistories.stream()
                .map(this::mapToSynchronizedStatusHistory)
                .toList();
    }

    private SynchronizedStatusHistory mapToSynchronizedStatusHistory(OrderStatus statusHistory) {
        SynchronizedStatusHistory syncHistory = new SynchronizedStatusHistory();
        syncHistory.setStatus(statusHistory.getOrderProcess());
        syncHistory.setChangedAt(statusHistory.getDishOrderStatusDatetime());
        syncHistory.setDishOrderId(statusHistory.getId());
        return syncHistory;
    }


}
