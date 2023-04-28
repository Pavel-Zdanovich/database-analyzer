package com.example.databaseanalyzer.dto;

import lombok.Data;

@Data
public class Corrections extends Credentials {

    private String database;

    private String schema;

    private String table;

    private String column;

}
