package com.example.databaseanalyzer.web;

import com.example.databaseanalyzer.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @GetMapping
    public Page<?> readAll(@SortDefault Pageable pageable) {
        return this.tableService.findAll(pageable);
    }

    @GetMapping("/{name}")
    public Page<?> readByName(@PathVariable String name, @SortDefault Pageable pageable) {
        return this.tableService.findByNameLike(name, pageable);
    }
}
