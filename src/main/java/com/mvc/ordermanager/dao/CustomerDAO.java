package com.mvc.ordermanager.dao;

import com.mvc.ordermanager.dao.mapper.CustomerRowMapper;
import com.mvc.ordermanager.resource.avro.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CustomerDAO implements ICustomerDAO {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    @Override
    public Customer getCustomer(Long id, final int timeout) throws Exception {
        try {
            JdbcTemplate select = new JdbcTemplate(this.dataSource);
            select.setQueryTimeout(timeout);
            List<Customer> customer = select.query("SELECT * FROM customer WHERE id = ?",
                    new Object[] {id},
                    new CustomerRowMapper());
            return customer.get(0);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
