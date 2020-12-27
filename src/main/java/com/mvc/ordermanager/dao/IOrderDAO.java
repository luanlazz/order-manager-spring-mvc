package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.model.OrderBean;
import com.mvc.ordermanager.resource.avro.Order;

public interface IOrderDAO {

    public OrderBean getOrder(final Integer id, final int timeout) throws Exception;
    public void saveOrder(final Order order, final int timeout) throws Exception;

}
