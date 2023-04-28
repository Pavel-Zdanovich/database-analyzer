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
public class Database {

    @Id
    private UUID id;

    @Setter
    private String name;

    @ManyToOne
    @JsonIgnore
    @Setter
    private Statistics statistics;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Map<String, Schema> schemas = new HashMap<>();

    public Database() {
        this.id = UUID.randomUUID();
    }

    public Schema get(Object name) {
        return this.schemas.get(name);
    }

    public Schema put(String name, Schema schema) {
        schema.setDatabase(this);
        return this.schemas.put(name, schema);
    }

}
