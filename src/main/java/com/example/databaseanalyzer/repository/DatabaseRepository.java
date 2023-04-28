package com.example.databaseanalyzer.repository;

import com.example.databaseanalyzer.model.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DatabaseRepository extends JpaRepository<Database, UUID> {
}
