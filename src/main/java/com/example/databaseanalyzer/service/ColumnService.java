package com.example.databaseanalyzer.service;

import com.example.databaseanalyzer.model.Column;
import com.example.databaseanalyzer.repository.ColumnRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class ColumnService {

    private final ColumnRepository columnRepository;

    public <S extends Column> S save(S entity) {
        return this.columnRepository.save(entity);
    }

    public Page<Column> findAll(Pageable pageable) {
        return this.columnRepository.findAll(pageable);
    }

    public Page<Column> findByNameLike(String name, Pageable pageable) {
        return this.columnRepository.findByNameLike(name, pageable);
    }
}
