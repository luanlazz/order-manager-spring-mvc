package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.resource.avro.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class InventoryDAO implements IInventoryDAO {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public Integer getStock(Product product) throws Exception {
        try {
            JdbcTemplate select = new JdbcTemplate(dataSource);

            return select.queryForObject("SELECT qtyTotal FROM inventory WHERE product = ?::Product",
                    new Object[]{product.toString()},
                    Integer.class);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public Integer getReserved(Product product) throws Exception {
        try {
            JdbcTemplate select = new JdbcTemplate(dataSource);

            return select.queryForObject("SELECT qtyReserved FROM inventory WHERE product = ?::Product",
                    new Object[]{product.toString()},
                    Integer.class);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void reserveStock(Product product, Integer quantity, final int timeout) throws Exception {
        try {
            JdbcTemplate update = new JdbcTemplate(dataSource);
            update.setQueryTimeout(timeout);
            update.update("UPDATE inventory set qtyReserved = qtyReserved + ? WHERE product = ?::Product",
                    quantity,
                    product.toString());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
