package com.mvc.ordermanager.service;

import com.mvc.ordermanager.model.EmailTuple;
import org.springframework.stereotype.Service;

@Service
public class Emailer implements IEmailer {

    @Override
    public void sendEmail(final EmailTuple details) {
        System.out.println("Sending email: " +
                "\nCustomer: " + details.customer.toString() +
                "\nOrder: " + details.order.toString() +
                "\nPayment: " + details.payment.toString());
    }
}
