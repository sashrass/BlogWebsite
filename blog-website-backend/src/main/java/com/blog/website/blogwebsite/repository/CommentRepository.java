package com.blog.website.blogwebsite.repository;

import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.Comment;
import com.blog.website.blogwebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
