package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.resource.avro.Customer;

public interface ICustomerDAO {

    Customer getCustomer(final Long id, final int timeout) throws Exception;
}
