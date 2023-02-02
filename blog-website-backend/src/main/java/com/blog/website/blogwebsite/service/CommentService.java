package com.blog.website.blogwebsite.service;

import com.blog.website.blogwebsite.dto.CommentsDto;
import com.blog.website.blogwebsite.exceptions.BlogWebsiteException;
import com.blog.website.blogwebsite.exceptions.PostNotFoundException;
import com.blog.website.blogwebsite.model.Comment;
import com.blog.website.blogwebsite.model.NotificationEmail;
import com.blog.website.blogwebsite.model.Post;
import com.blog.website.blogwebsite.model.User;
import com.blog.website.blogwebsite.repository.CommentRepository;
import com.blog.website.blogwebsite.repository.PostRepository;
import com.blog.website.blogwebsite.repository.UserRepository;
import com.blog.website.blogwebsite.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post " + "'" + post.getPostName() + "'");
        sendCommentNotification(message, post.getUser() ,authService.getCurrentUser());
    }

    private void sendCommentNotification(String message, User userToSendEmail, User userCommented) {
        mailService.sendMail(new NotificationEmail(userCommented.getUsername() + " Commented on your post", userToSendEmail.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new BlogWebsiteException("Comments contains unacceptable language");
        }
        return false;
    }
}
