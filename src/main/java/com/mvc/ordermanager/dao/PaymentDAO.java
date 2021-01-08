package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.resource.avro.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class PaymentDAO implements IPaymentDAO {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public void savePayment(final Payment payment, final int timeout) throws Exception {
        try {
            JdbcTemplate insert = new JdbcTemplate(this.dataSource);
            insert.setQueryTimeout(timeout);
            insert.update("INSERT INTO payment (ID, ORDERID, CCY, AMOUNT) " +
                            "VALUES(?, ?, ?, ?)",
                    Integer.valueOf(payment.getId().toString()),
                    Integer.valueOf(payment.getOrderId().toString()),
                    payment.getCcy(),
                    payment.getAmount());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public Integer getNextId(final int timeout) throws Exception{
        try {
            JdbcTemplate select = new JdbcTemplate(dataSource);
            select.setQueryTimeout(timeout);
            return select.queryForObject("SELECT NEXTVAL('payment_seq')",
                    new Object[]{},
                    Integer.class);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
