package com.mvc.ordermanager.dao.mapper;

import com.mvc.ordermanager.resource.avro.Customer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerResultSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(final ResultSet rs) throws SQLException, DataAccessException {
        try {
            Customer customer = new Customer();
            customer.setId(rs.getLong(1));
            customer.setFirstName(rs.getString(2));
            customer.setLastName(rs.getString(3));
            customer.setEmail(rs.getString(4));
            customer.setAddress(rs.getString(5));
            customer.setLevel(rs.getString(6));

            return customer;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
