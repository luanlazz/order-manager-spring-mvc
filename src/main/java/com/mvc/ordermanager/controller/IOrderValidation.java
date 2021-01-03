package com.mvc.ordermanager.controller;

import com.mvc.ordermanager.resource.avro.Order;

public interface IOrderValidation {

    public void validate(Order order);
}
