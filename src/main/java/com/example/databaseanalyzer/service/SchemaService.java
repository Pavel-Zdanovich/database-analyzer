package com.example.databaseanalyzer.service;

import com.example.databaseanalyzer.model.Schema;
import com.example.databaseanalyzer.repository.SchemaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SchemaService {

    private final SchemaRepository schemaRepository;

    public <S extends Schema> S save(S entity) {
        return this.schemaRepository.save(entity);
    }

    public Page<Schema> findAll(Pageable pageable) {
        return this.schemaRepository.findAll(pageable);
    }

    public Page<Schema> findByNameLike(String name, Pageable pageable) {
        return this.schemaRepository.findByNameLike(name, pageable);
    }
}
