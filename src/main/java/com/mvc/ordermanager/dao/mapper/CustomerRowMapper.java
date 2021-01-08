package com.mvc.ordermanager.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper {

    @Override
    public Object mapRow(final ResultSet resultSet, final int i) throws SQLException {
        try {
            CustomerResultSetExtractor extractor = new CustomerResultSetExtractor();
            return extractor.extractData(resultSet);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
