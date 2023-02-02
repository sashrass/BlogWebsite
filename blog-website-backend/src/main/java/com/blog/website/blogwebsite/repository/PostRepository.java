package com.blog.website.blogwebsite.repository;

import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.Section;
import com.blog.website.blogwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySection(Section section);

    List<Post> findByUser(User user);
}
