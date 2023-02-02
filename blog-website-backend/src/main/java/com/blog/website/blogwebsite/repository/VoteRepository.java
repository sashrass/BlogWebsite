package com.blog.website.blogwebsite.repository;

import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.User;
import com.blog.website.blogwebsite.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
    Optional<Vote> findByPostAndUser(Post post, User currentUser);
    void deleteByPostAndUser(Post post, User user);
}
