package com.comparus.multipledatasource.controller;

import com.comparus.multipledatasource.model.User;
import com.comparus.multipledatasource.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.comparus.multipledatasource.repository.UserRepository.EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class UserControllerTest {

    private final String usersEndpoint = "/users";

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    UserService userService;

    @Container
    public static PostgreSQLContainer<?> pgContainer = new PostgreSQLContainer<>("postgres:15.8")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("password")
            .withInitScript("postgres.sql");

    @Container
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:9.0.1")
            .withDatabaseName("user_db")
            .withUsername("user")
            .withPassword("password")
            .withInitScript("mysql.sql");

    @BeforeAll
    static void beforeAll() {
        System.setProperty("POSTGRES_URL", pgContainer.getJdbcUrl());
        System.setProperty("MYSQL_URL", mysqlContainer.getJdbcUrl());
    }

    @Test
    void findAllUsersWithoutParams() {
        ResponseEntity<List<User>> usersResponse = restTemplate.exchange(
                usersEndpoint,
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<>() {
                });
        List<User> users = usersResponse.getBody();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(12, users.size());
    }

    @Test
    void findAllUsersWith_databaseParameter_success() {
        ResponseEntity<List<User>> usersResponse = restTemplate.exchange(
                usersEndpoint + "?database=postgres",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<>() {
                });
        List<User> users = usersResponse.getBody();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(6, users.size());
    }

    @Test
    void findAllUsersWith_databaseParameter_failed() {
        ResponseEntity<String> usersResponse = restTemplate.exchange(
                usersEndpoint + "?database=wrong",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<>() {
                });
        String exceptionResponse = usersResponse.getBody();

        assertEquals(HttpStatus.NOT_FOUND, usersResponse.getStatusCode());
        assertNotNull(exceptionResponse);
        assertEquals(EXCEPTION_MESSAGE, exceptionResponse);
    }

    @Test
    void findAllUsersWith_nonExistedParams() {
        ResponseEntity<List<User>> usersResponse = restTemplate.exchange(
                usersEndpoint + "?any=value&other=value",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<>() {
                });
        List<User> users = usersResponse.getBody();

        assertEquals(HttpStatus.OK, usersResponse.getStatusCode());
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(12, users.size());
    }
}