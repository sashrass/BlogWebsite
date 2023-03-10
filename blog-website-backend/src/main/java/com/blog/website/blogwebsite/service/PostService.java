package com.blog.website.blogwebsite.service;

import com.blog.website.blogwebsite.dto.PostRequest;
import com.blog.website.blogwebsite.dto.PostResponse;
import com.blog.website.blogwebsite.exceptions.PostNotFoundException;
import com.blog.website.blogwebsite.exceptions.SectionNotFoundException;
import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.Section;
import com.blog.website.blogwebsite.repository.PostRepository;
import com.blog.website.blogwebsite.repository.SectionRepository;
import com.blog.website.blogwebsite.repository.UserRepository;
import com.blog.website.blogwebsite.mapper.PostMapper;
import com.blog.website.blogwebsite.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SectionRepository sectionRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Section section = sectionRepository.findByName(postRequest.getSectionName())
                .orElseThrow(() -> new SectionNotFoundException(postRequest.getSectionName()));
        postRepository.save(postMapper.map(postRequest, section, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySection(Long sectionId) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId.toString()));
        List<Post> posts = postRepository.findAllBySection(section);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }
}
