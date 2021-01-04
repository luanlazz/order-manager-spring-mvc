package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.model.OrderBean;
import com.mvc.ordermanager.resource.avro.Order;

import java.sql.Timestamp;

public interface IOrderDAO {

    public OrderBean getOrder(final Integer id, final int timeout) throws Exception;
    public void saveOrder(final Order order, final int timeout) throws Exception;
    public Double getTotalByCustomer(final Long customer_id, final Timestamp period) throws Exception;
}
