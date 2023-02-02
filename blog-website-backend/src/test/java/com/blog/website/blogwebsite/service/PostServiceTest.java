package com.blog.website.blogwebsite.service;

import com.blog.website.blogwebsite.dto.PostRequest;
import com.blog.website.blogwebsite.dto.PostResponse;
import com.blog.website.blogwebsite.mapper.PostMapper;
import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.Section;
import com.blog.website.blogwebsite.model.User;
import com.blog.website.blogwebsite.repository.PostRepository;
import com.blog.website.blogwebsite.repository.SectionRepository;
import com.blog.website.blogwebsite.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private SectionRepository sectionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private PostService postService;

    @BeforeEach
    public void setup() {
        postService = new PostService(postRepository, sectionRepository, userRepository, authService, postMapper);
    }

    @Test
    @DisplayName("Should Retrieve Post by Id")
    public void shouldFindPostById() {
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null);
        PostResponse expectedPostResponse = new PostResponse(123L, "First Post", "http://url.site", "Test",
                "Test User", "Test Subredit", 0, 0, "1 Hour Ago", false, false);

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPost(123L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());
    }

    @Test
    @DisplayName("Should Save Posts")
    public void shouldSavePosts() {
        User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
        Section section = new Section(123L, "First Section", "Section Description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(123L, "First Post", "http://url.site", "Test",
                0, null, Instant.now(), null);
        PostRequest postRequest = new PostRequest(null, "First Section", "First Post", "http://url.site", "Test");

        Mockito.when(sectionRepository.findByName("First Section"))
                .thenReturn(Optional.of(section));
        Mockito.when(authService.getCurrentUser())
                .thenReturn(currentUser);
        Mockito.when(postMapper.map(postRequest, section, currentUser))
                .thenReturn(post);

        postService.save(postRequest);
        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
        Assertions.assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First Post");
    }
}
