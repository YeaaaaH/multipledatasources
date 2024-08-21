package com.comparus.multipledatasource.model;

import lombok.Data;

import java.util.Map;

@Data
public class DBProperties {
    private String name;
    private String strategy;
    private String url;
    private String table;
    private String user;
    private String password;
    private Map<String, String> mapping;
}