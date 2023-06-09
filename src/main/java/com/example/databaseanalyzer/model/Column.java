package com.example.databaseanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Column {

    @Id
    private UUID id;

    private String name;

    private String type;

    private Integer size;

    @ManyToOne
    @JsonIgnore
    private Table table;

    public Column() {
        this.id = UUID.randomUUID();
    }
}
