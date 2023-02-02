package com.blog.website.blogwebsite.mapper;

import com.blog.website.blogwebsite.dto.SectionDto;
import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.Section;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(section.getPosts()))")
    SectionDto mapSectionToDto(Section section);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Section mapDtoToSection(SectionDto sectionDto);
}
