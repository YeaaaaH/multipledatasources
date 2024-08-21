package com.comparus.multipledatasource.repository;

import com.comparus.multipledatasource.config.ParametrizedJdbcTemplate;
import com.comparus.multipledatasource.exception.DataBaseNameParamException;
import com.comparus.multipledatasource.model.DBProperties;
import com.comparus.multipledatasource.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

@Repository
public class UserRepository {
    public static final String EXCEPTION_MESSAGE = "Provide correct databaseName parameter or ignore it";
    public static final String PREFIX = " WHERE ";
    public static final String DELIMITER = " AND ";
    public static final String SUFFIX = "";
    public static final String QUERY_BASE = "SELECT * FROM ";

    private final Map<String, ParametrizedJdbcTemplate> parametrizedJdbcTemplateMap;

    public UserRepository(Map<String, ParametrizedJdbcTemplate> parametrizedJdbcTemplateMap) {
        this.parametrizedJdbcTemplateMap = parametrizedJdbcTemplateMap;
    }

    public List<User> findAllUsersWithParams(Map<String, String> params) {
        String databaseName = params.get("database");
        if (databaseName != null && !databaseName.isEmpty()) {
            ParametrizedJdbcTemplate parametrizedJdbcTemplate =
                    Optional.ofNullable(parametrizedJdbcTemplateMap.get(databaseName))
                            .orElseThrow(() -> new DataBaseNameParamException(EXCEPTION_MESSAGE));
            return findAllFromTemplate(parametrizedJdbcTemplate, params);
        } else {
            return parametrizedJdbcTemplateMap.values()
                    .stream()
                    .map(template -> findAllFromTemplate(template, params))
                    .flatMap(Collection::stream)
                    .toList();
        }
    }

    private List<User> findAllFromTemplate(ParametrizedJdbcTemplate template, Map<String, String> params) {
        String preparedQuery = prepareQueryWithParams(template.getDbProperties(), params);
        RowMapper<User> userRowMapper = buildUserRowMapper(template.getDbProperties());
        return template.getJdbcTemplate().query(preparedQuery, userRowMapper);
    }

    private String prepareQueryWithParams(DBProperties properties, Map<String, String> params) {
        StringBuilder queryBuilder = new StringBuilder(QUERY_BASE + properties.getTable());
        StringJoiner whereClause = new StringJoiner(DELIMITER, PREFIX, SUFFIX);
        Map<String, String> propertiesMapping = properties.getMapping();
        params.forEach((key, value) -> {
            String mapParam = propertiesMapping.get(key);
            if (mapParam != null) {
                whereClause.add(mapParam + " = '" + value  + "'");
            }
        });
        if (whereClause.toString().equals(PREFIX)) {
            return queryBuilder.toString();
        } else {
            return queryBuilder.append(whereClause).toString();
        }
    }

    private RowMapper<User> buildUserRowMapper(DBProperties properties) {
        Map<String, String> mapping = properties.getMapping();
        return (key, value) -> new User(
                key.getString(mapping.get("id")),
                key.getString(mapping.get("username")),
                key.getString(mapping.get("name")),
                key.getString(mapping.get("surname"))
        );
    }
}