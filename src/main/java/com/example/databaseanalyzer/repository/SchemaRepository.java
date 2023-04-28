package com.example.databaseanalyzer.repository;

import com.example.databaseanalyzer.model.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchemaRepository extends JpaRepository<Schema, UUID> {
}
