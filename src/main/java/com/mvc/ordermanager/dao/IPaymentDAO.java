package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.resource.avro.Payment;

public interface IPaymentDAO {

    void savePayment(final Payment payment, final int timeout) throws Exception;
    Integer getNextId(final int timeout) throws Exception;
}
