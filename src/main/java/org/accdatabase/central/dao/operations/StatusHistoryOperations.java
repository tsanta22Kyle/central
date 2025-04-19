package org.accdatabase.central.dao.operations;

import org.accdatabase.central.dao.Datasource;
import org.accdatabase.central.model.OrderProcess;
import org.accdatabase.central.model.SynchronizedStatusHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatusHistoryOperations {
    @Autowired
    private Datasource datasource;

    public void saveAll(List<SynchronizedStatusHistory> historyList) throws SQLException {
        String query = "INSERT INTO synchronized_status_history (dish_order_id, status, timestamp ) VALUES (?, ?, ?)";

        try (Connection connection = datasource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            for (SynchronizedStatusHistory s : historyList) {
                stmt.setString(1, s.getDishOrderId());
                stmt.setString(2, s.getStatus().toString());
                stmt.setTimestamp(3, Timestamp.valueOf(s.getChangedAt()));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    public List<SynchronizedStatusHistory> getByDishOrderId(String dishOrderId) throws SQLException {
        List<SynchronizedStatusHistory> historyList = new ArrayList<SynchronizedStatusHistory>();

        String query = "SELECT * FROM synchronized_status_history WHERE dish_order_id = ?";
        try (Connection connection = datasource.getConnection();
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, dishOrderId);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    SynchronizedStatusHistory history = new SynchronizedStatusHistory();
                    history.setDishOrderId(rs.getString("dish_order_id"));
                    history.setStatus(OrderProcess.valueOf(rs.getString("status")));
                    history.setChangedAt(rs.getTimestamp("timestamp").toLocalDateTime());
                    historyList.add(history);
                }
            }
        }
        return historyList;
    }


}
