package com.blog.website.blogwebsite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
