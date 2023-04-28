package com.example.databaseanalyzer.web;

import com.example.databaseanalyzer.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/column")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    @GetMapping
    public Page<?> readAll(@SortDefault Pageable pageable) {
        return this.columnService.findAll(pageable);
    }

    @GetMapping("/{name}")
    public Page<?> readByName(@PathVariable String name, @SortDefault Pageable pageable) {
        return this.columnService.findByNameLike(name, pageable);
    }
}
