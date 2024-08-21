package com.comparus.multipledatasource.config;

import com.comparus.multipledatasource.model.DBProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties
@Component
@Getter
@Setter
public class DataSourceProperties {
    private final List<DBProperties> dataSources;

    public DataSourceProperties(List<DBProperties> dataSources) {
        this.dataSources = dataSources;
    }
}
