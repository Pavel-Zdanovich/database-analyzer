package com.example.databaseanalyzer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
public class Table {

    @Id
    private UUID id;

    @Setter
    private String name;

    @ManyToOne
    @JsonIgnore
    @Setter
    private Schema schema;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Map<String, Column> columns = new HashMap<>();

    public Table() {
        this.id = UUID.randomUUID();
    }

    public Column get(Object name) {
        return this.columns.get(name);
    }

    public Column put(String name, Column column) {
        column.setTable(this);
        return this.columns.put(name, column);
    }

}
