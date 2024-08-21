package com.comparus.multipledatasource.config;

import com.comparus.multipledatasource.model.DBProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DataSourceConfig {
    public static final String MY_SQL = "mysql";
    public static final String POSTGRES = "postgres";

    public static final Map<String, String> DRIVERS_MAP = Map.of(
            POSTGRES, "org.postgresql.Driver",
            MY_SQL, "com.mysql.cj.jdbc.Driver"
    );

    private final DataSourceProperties dataSourceProperties;

    public DataSourceConfig(DataSourceProperties dataSources) {
        this.dataSourceProperties = dataSources;
    }

    @Bean
    public Map<String, ParametrizedJdbcTemplate> parametrizedJdbcTemplateMap() {
        return dataSourceProperties.getDataSources()
                .stream()
                .collect(Collectors.toMap(
                        DBProperties::getStrategy,
                        property -> new ParametrizedJdbcTemplate(new JdbcTemplate(initDataSource(property)), property)
                ));
    }

    private DataSource initDataSource(DBProperties dbProperties) {
        return DataSourceBuilder.create()
                .driverClassName(DRIVERS_MAP.get(dbProperties.getStrategy()))
                .url(dbProperties.getUrl())
                .username(dbProperties.getUser())
                .password(dbProperties.getPassword())
                .build();
    }
}
