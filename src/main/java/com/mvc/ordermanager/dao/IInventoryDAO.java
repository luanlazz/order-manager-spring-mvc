package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.resource.avro.Product;

public interface IInventoryDAO {

    Integer getStock(final Product product) throws Exception;
    Integer getReserved(final Product product) throws Exception;
    void reserveStock(final Product product, final Integer quantity, final int timeout) throws Exception;
}
