package com.mvc.ordermanager.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper {

    @Override
    public Object mapRow(final ResultSet resultSet, final int i) throws SQLException {
        try {
            OrderResultSetExtractor extractor = new OrderResultSetExtractor();
            return extractor.extractData(resultSet);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }
}
