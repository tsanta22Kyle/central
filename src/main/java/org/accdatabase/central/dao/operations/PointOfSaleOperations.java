package org.accdatabase.central.dao.operations;

import org.accdatabase.central.dao.Datasource;
import org.accdatabase.central.model.PointOfSale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class PointOfSaleOperations {
    @Autowired
    private Datasource dataSource;



    public List<PointOfSale> findAll() throws SQLException {
        String query = "SELECT id, name, api_url, api_key FROM point_of_sale";
        List<PointOfSale> result = new ArrayList<>();

        try (   Connection connection = dataSource.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PointOfSale po = new PointOfSale();
                po.setId(rs.getString("id"));
                po.setName(rs.getString("name"));
                po.setApiUrl(rs.getString("api_url"));
                po.setApiKey(rs.getString("api_key"));
                result.add(po);
            }
        }

        return result;
    }
}
