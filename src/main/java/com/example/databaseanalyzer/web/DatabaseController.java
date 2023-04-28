package com.example.databaseanalyzer.web;

import com.example.databaseanalyzer.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/database")
@RequiredArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    @GetMapping
    public Page<?> readAll(@SortDefault Pageable pageable) {
        return this.databaseService.findAll(pageable);
    }
}
