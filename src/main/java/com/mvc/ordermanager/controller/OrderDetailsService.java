package com.mvc.ordermanager.controller;

import com.mvc.ordermanager.resource.avro.Order;
import com.mvc.ordermanager.resource.avro.OrderState;
import com.mvc.ordermanager.resource.avro.Product;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService implements IOrderValidation{

    public void validate(Order order) {
        if (order.getQuantity() < 0) {
            order.setState(OrderState.FAILED);
        }
        if (order.getPrice() < 0) {
            order.setState(OrderState.FAILED);
        }
        if (order.getProduct() == null) {
            order.setState(OrderState.FAILED);
        }
    }
}
