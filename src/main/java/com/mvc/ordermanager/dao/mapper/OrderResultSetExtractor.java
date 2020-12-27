package com.mvc.ordermanager.dao.mapper;

import com.mvc.ordermanager.model.OrderBean;
import com.mvc.ordermanager.resource.avro.OrderState;
import com.mvc.ordermanager.resource.avro.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderResultSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(final ResultSet rs) throws SQLException, DataAccessException {
        try{
            OrderBean orderBean = new OrderBean();
            orderBean.setId(rs.getString(1));
            orderBean.setCustomerId(rs.getLong(2));
            orderBean.setState(OrderState.valueOf(rs.getString(3 )));
            orderBean.setProduct(Product.valueOf(rs.getString(4)));
            orderBean.setQuantity(rs.getInt(5));
            orderBean.setPrice(rs.getDouble(6));

            return orderBean;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
