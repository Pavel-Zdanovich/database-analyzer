package com.example.databaseanalyzer.service;

import com.example.databaseanalyzer.model.Table;
import com.example.databaseanalyzer.repository.TableRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public <S extends Table> S save(S entity) {
        return this.tableRepository.save(entity);
    }

    public Page<Table> findAll(Pageable pageable) {
        return this.tableRepository.findAll(pageable);
    }

    public Page<Table> findByNameLike(String name, Pageable pageable) {
        return this.tableRepository.findByNameLike(name, pageable);
    }
}
