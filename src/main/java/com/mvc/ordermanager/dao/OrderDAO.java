package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.dao.mapper.OrderRowMapper;
import com.mvc.ordermanager.model.OrderBean;
import com.mvc.ordermanager.resource.avro.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDAO implements IOrderDAO {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public OrderBean getOrder(final Integer id, final int timeout) throws Exception {
        try {
            JdbcTemplate select = new JdbcTemplate(dataSource);
            select.setQueryTimeout(timeout);
            List<OrderBean> orders = select.query("select * from \"order\" where id = ?",
                    new Object[] {id},
                    new OrderRowMapper());
            return orders.get(0);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public void saveOrder(final Order order, final int timeout) throws Exception {
        try {
            JdbcTemplate insert = new JdbcTemplate(dataSource);
            insert.setQueryTimeout(timeout);
            insert.update("INSERT INTO \"order\" (ID, CUSTOMERID, STATE, PRODUCT, QUANTITY, PRICE) " +
                            "VALUES(NEXTVAL('order_seq'), ?, ?::OrderState, ?::Product, ?, ?)",
                    order.getCustomerId(),
                    order.getState().name(),
                    order.getProduct().name(),
                    order.getQuantity(),
                    order.getPrice());
        }  catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public Double getTotalByCustomer(final Long customer_id, final Timestamp period) throws Exception {
        try {
            JdbcTemplate select = new JdbcTemplate(dataSource);
            return select.queryForObject("SELECT COALESCE(SUM(quantity * price), 0) FROM \"order\" WHERE customerid = ? AND created_at >= ?",
                    new Object[] {customer_id, period},
                    Double.class);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}