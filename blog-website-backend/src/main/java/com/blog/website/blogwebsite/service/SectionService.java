package com.blog.website.blogwebsite.service;

import com.blog.website.blogwebsite.dto.SectionDto;
import com.blog.website.blogwebsite.exceptions.BlogWebsiteException;
import com.blog.website.blogwebsite.model.Section;
import com.blog.website.blogwebsite.repository.SectionRepository;
import com.blog.website.blogwebsite.mapper.SectionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class SectionService {

    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;

    @Transactional
    public SectionDto save(SectionDto sectionDto) {
        Section save = sectionRepository.save(sectionMapper.mapDtoToSection(sectionDto));
        sectionDto.setId(save.getId());
        return sectionDto;
    }

    @Transactional(readOnly = true)
    public List<SectionDto> getAll() {
        return sectionRepository.findAll()
                .stream()
                .map(sectionMapper::mapSectionToDto)
                .collect(toList());
    }

    @Transactional
    public SectionDto getSection(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new BlogWebsiteException("No section found with ID - " + id));
        return sectionMapper.mapSectionToDto(section);
    }
}
