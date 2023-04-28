package com.example.databaseanalyzer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Credentials {

    @NotBlank
    private String url;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}
