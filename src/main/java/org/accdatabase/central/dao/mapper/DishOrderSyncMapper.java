package org.accdatabase.central.dao.mapper;

import lombok.SneakyThrows;
import org.accdatabase.central.dao.operations.StatusHistoryOperations;
import org.accdatabase.central.model.SynchronizedDishOrder;
import org.accdatabase.central.model.SynchronizedStatusHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Function;
@Component
public class DishOrderSyncMapper implements Function<ResultSet, SynchronizedDishOrder> {

    @Autowired
    private StatusHistoryOperations statusHistoryOperations;

    @SneakyThrows
    @Override
    public SynchronizedDishOrder apply(ResultSet resultSet) {
        SynchronizedDishOrder dishOrder = new SynchronizedDishOrder();
        List<SynchronizedStatusHistory> statusHistoryList = statusHistoryOperations.getByDishOrderId(resultSet.getString("id"));
        dishOrder.setDishOrderId(resultSet.getString("id"));
        dishOrder.setOrderStatus(statusHistoryList);
        dishOrder.setDishName(resultSet.getString("dish_name"));
        dishOrder.setDishId(resultSet.getString("dish_id"));
        dishOrder.setQuantity(resultSet.getInt("quantity"));
        dishOrder.setSyncDate(resultSet.getTimestamp("synchronization_date").toLocalDateTime());
        dishOrder.setSaleId(resultSet.getString("point_of_sale_id"));
        return dishOrder;
    }
}
