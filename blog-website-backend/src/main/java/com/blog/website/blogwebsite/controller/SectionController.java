package com.blog.website.blogwebsite.controller;

import com.blog.website.blogwebsite.dto.SectionDto;
import com.blog.website.blogwebsite.service.SectionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/section")
@AllArgsConstructor
@Slf4j
public class SectionController {

    private final SectionService sectionService;

    @PostMapping
    public ResponseEntity<SectionDto> createSection(@RequestBody SectionDto sectionDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sectionService.save(sectionDto));
    }

    @GetMapping
    public ResponseEntity<List<SectionDto>> getAllSections() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sectionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionDto> getSection(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(sectionService.getSection(id));
    }
}
