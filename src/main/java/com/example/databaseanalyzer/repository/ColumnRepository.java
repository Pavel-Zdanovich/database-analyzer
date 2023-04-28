package com.example.databaseanalyzer.repository;

import com.example.databaseanalyzer.model.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ColumnRepository extends JpaRepository<Column, UUID> {
}
