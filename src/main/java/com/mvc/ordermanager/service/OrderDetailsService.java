package com.mvc.ordermanager.service;

import com.mvc.ordermanager.resource.avro.Order;
import com.mvc.ordermanager.resource.avro.OrderState;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService implements IOrderValidation {

    public void validate(Order order) {
        if (!isValid(order)){
            order.setState(OrderState.FAILED);
        }
    }

    public boolean isValid(Order order) {
        if (order.getQuantity() < 0) {
            return false;
        }
        if (order.getPrice() < 0) {
            return false;
        }
        return order.getProduct() != null;
    }
}
