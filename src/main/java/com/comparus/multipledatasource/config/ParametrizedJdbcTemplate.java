package com.comparus.multipledatasource.config;

import com.comparus.multipledatasource.model.DBProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

@Data
@AllArgsConstructor
public class ParametrizedJdbcTemplate {
    private JdbcTemplate jdbcTemplate;
    private DBProperties dbProperties;
}