package com.mvc.ordermanager.model;

import com.mvc.ordermanager.resource.avro.Customer;
import com.mvc.ordermanager.resource.avro.Payment;

public class EmailTuple {

    public final OrderBean order;
    public final Payment payment;
    public final Customer customer;

    public EmailTuple(OrderBean order, Payment payment, Customer customer) {
        this.order = order;
        this.payment = payment;
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "EmailTuple{" +
                "order=" + order +
                ", payment=" + payment +
                ", customer=" + customer +
                '}';
    }
}
