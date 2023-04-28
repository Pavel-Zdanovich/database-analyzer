package com.example.databaseanalyzer.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Getter
public class Statistics {

    @Id
    private UUID id;

    @Setter
    private String name;

    @Setter
    private String version;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Map<String, Database> databases = new HashMap<>();

    public Statistics() {
        this.id = UUID.randomUUID();
    }

    public Database get(Object name) {
        return this.databases.get(name);
    }

    public Database put(String name, Database database) {
        database.setStatistics(this);
        return this.databases.put(name, database);
    }
}
