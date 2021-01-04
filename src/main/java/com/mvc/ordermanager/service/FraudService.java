package com.mvc.ordermanager.service;

import com.mvc.ordermanager.dao.IOrderDAO;
import com.mvc.ordermanager.resource.avro.Order;
import com.mvc.ordermanager.resource.avro.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service
public class FraudService implements IOrderValidation {

    private final IOrderDAO orderDAO;
    private static final int FRAUD_LIMIT = 2000;

    @Autowired
    public FraudService(IOrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void validate(Order order) throws Exception {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(calendar.getTime());
            calendar.add(Calendar.HOUR, -1);
            Date now = calendar.getTime();
            Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

            Double total = this.orderDAO.getTotalByCustomer(order.getCustomerId(), currentTimestamp);

            if (total >= FRAUD_LIMIT) {
                order.setState(OrderState.FAILED);
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
