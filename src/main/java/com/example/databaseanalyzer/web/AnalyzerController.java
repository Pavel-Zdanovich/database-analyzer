package com.example.databaseanalyzer.web;

import com.example.databaseanalyzer.dto.Corrections;
import com.example.databaseanalyzer.dto.Credentials;
import com.example.databaseanalyzer.model.Statistics;
import com.example.databaseanalyzer.service.Analyzer;
import com.example.databaseanalyzer.service.StatisticsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AnalyzerController {

    private final Analyzer analyzer;

    private final StatisticsService statisticsService;

    @PostMapping("/analysis")
    @ResponseStatus(HttpStatus.CREATED)
    public Statistics analyze(@Valid @RequestBody Credentials credentials) {
        Statistics statistics = this.analyzer.analyze(credentials);
        return this.statisticsService.save(statistics);
    }

    @PutMapping("/capitalize")
    public void capitalize(@Valid @RequestBody Corrections corrections) {
        this.analyzer.capitalize(corrections);
    }
}
