package org.accdatabase.central.dao.operations;

import org.accdatabase.central.dao.Datasource;
import org.accdatabase.central.dao.mapper.DishOrderSyncMapper;
import org.accdatabase.central.model.SynchronizedDishOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class SynchronizedDishOrderOperations {

    @Autowired
    private Datasource datasource;
    @Autowired
    private DishOrderSyncMapper dishOrderSyncMapper;

    public List<SynchronizedDishOrder> saveAll(List<SynchronizedDishOrder> dishOrders) throws SQLException {
        List<SynchronizedDishOrder> synchronizedDishOrders = new ArrayList<SynchronizedDishOrder>();
        String query = "INSERT INTO synchronized_dish_order ( point_of_sale_id, dish_id, dish_name, quantity) VALUES ( ?, ?, ?, ?) ON CONFLICT (id) DO UPDATE SET quantity=excluded.quantity RETURNING quantity,point_of_sale_id,dish_id,dish_name,id,synchronization_date";

        try (Connection connection = datasource.getConnection()) {
            dishOrders.forEach(d -> {
                try (
                        PreparedStatement stmt = connection.prepareStatement(query)
                ) {

                    String saleId = d.getSaleId()==null? UUID.randomUUID().toString(): d.getSaleId();
                   // stmt.setString(1, d.getDishOrderId());
                    stmt.setString(1, saleId);
                    stmt.setString(2, d.getDishId());
                    stmt.setInt(4, d.getQuantity());
                    stmt.setString(3, d.getDishName());
                    try(ResultSet rs = stmt.executeQuery()) {
                                while (rs.next()){
                                    synchronizedDishOrders.add(dishOrderSyncMapper.apply(rs));
                                }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return synchronizedDishOrders;
    }
}
