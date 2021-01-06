package com.mvc.ordermanager.service;

import com.mvc.ordermanager.dao.IInventoryDAO;
import com.mvc.ordermanager.resource.avro.Order;
import com.mvc.ordermanager.resource.avro.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService implements IOrderValidation {

    private final IInventoryDAO inventoryDAO;

    @Autowired
    public InventoryService(IInventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    @Override
    public void validate(Order order) throws Exception {
        try {
            Integer warehouseStockCount = this.inventoryDAO.getStock(order.getProduct());
            Integer reserved = this.inventoryDAO.getReserved(order.getProduct());

            if (warehouseStockCount - reserved - order.getQuantity() < 0) {
                order.setState(OrderState.FAILED);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
