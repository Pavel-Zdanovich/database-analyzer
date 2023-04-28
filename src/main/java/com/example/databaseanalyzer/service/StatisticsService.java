package com.example.databaseanalyzer.service;

import com.example.databaseanalyzer.model.Statistics;
import com.example.databaseanalyzer.repository.StatisticsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public <S extends Statistics> S save(S entity) {
        return statisticsRepository.save(entity);
    }

    public Page<Statistics> findAll(Pageable pageable) {
        return statisticsRepository.findAll(pageable);
    }

}
