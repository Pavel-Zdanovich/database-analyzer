package com.example.databaseanalyzer.service;

import com.example.databaseanalyzer.model.Database;
import com.example.databaseanalyzer.repository.DatabaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DatabaseService {

    private final DatabaseRepository databaseRepository;

    public <S extends Database> S save(S entity) {
        return databaseRepository.save(entity);
    }

    public Page<Database> findAll(Pageable pageable) {
        return databaseRepository.findAll(pageable);
    }
}
