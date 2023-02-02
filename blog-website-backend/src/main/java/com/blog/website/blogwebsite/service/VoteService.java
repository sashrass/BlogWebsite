package com.blog.website.blogwebsite.service;

import com.blog.website.blogwebsite.dto.VoteDto;
import com.blog.website.blogwebsite.exceptions.BlogWebsiteException;
import com.blog.website.blogwebsite.exceptions.PostNotFoundException;
import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.Vote;
import com.blog.website.blogwebsite.model.VoteType;
import com.blog.website.blogwebsite.repository.PostRepository;
import com.blog.website.blogwebsite.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findByPostAndUser(post, authService.getCurrentUser());
        boolean isRenegotiation = false;
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            throw new BlogWebsiteException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        else if (voteByPostAndUser.isPresent() &&
                !voteByPostAndUser.get().getVoteType()
                        .equals(voteDto.getVoteType())) {
            voteRepository.deleteByPostAndUser(post, authService.getCurrentUser());
            isRenegotiation = true;
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        if (!isRenegotiation) {
            voteRepository.save(mapToVote(voteDto, post));
        }
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
