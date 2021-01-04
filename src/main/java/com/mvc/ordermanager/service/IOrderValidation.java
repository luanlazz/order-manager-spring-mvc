package com.mvc.ordermanager.service;

import com.mvc.ordermanager.resource.avro.Order;

public interface IOrderValidation {

    public void validate(Order order) throws Exception;
}
