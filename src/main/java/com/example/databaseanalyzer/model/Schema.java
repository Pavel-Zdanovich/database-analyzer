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
public class Schema {

    @Id
    private UUID id;

    @Setter
    private String name;

    @ManyToOne
    @JsonIgnore
    @Setter
    private Database database;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Map<String, Table> tables = new HashMap<>();

    public Schema() {
        this.id = UUID.randomUUID();
    }

    public Table get(Object name) {
        return this.tables.get(name);
    }

    public Table put(String name, Table table) {
        table.setSchema(this);
        return this.tables.put(name, table);
    }

}
