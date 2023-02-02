package com.blog.website.blogwebsite.repository;

import com.blog.website.blogwebsite.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findByName(String sectionName);
}
