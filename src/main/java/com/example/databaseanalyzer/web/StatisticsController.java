package com.example.databaseanalyzer.web;

import com.example.databaseanalyzer.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public Page<?> readAll(@SortDefault Pageable pageable) {
        return this.statisticsService.findAll(pageable);
    }

    @GetMapping("/{name}")
    public Page<?> readByName(@PathVariable String name, @SortDefault Pageable pageable) {
        return this.statisticsService.findByNameLike(name, pageable);
    }
}
