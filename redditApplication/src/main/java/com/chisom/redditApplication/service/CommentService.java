package com.chisom.redditApplication.service;

import com.chisom.redditApplication.dto.CommentDto;
import com.chisom.redditApplication.exceptions.PostNotFoundException;
import com.chisom.redditApplication.mapper.CommentMapper;
import com.chisom.redditApplication.model.Comment;
import com.chisom.redditApplication.model.NotificationEmail;
import com.chisom.redditApplication.model.Post;
import com.chisom.redditApplication.model.Users;
import com.chisom.redditApplication.repository.CommentRepository;
import com.chisom.redditApplication.repository.PostRepository;
import com.chisom.redditApplication.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUsers().getUsername()
                + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUsers());
    }

    public void sendCommentNotification(String message, Users users) {
        mailService.sendMail(new NotificationEmail(users.getUsername()
                + " commented on your post.", users.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream().
                map(commentMapper::mapToDto)
                .collect(toList());
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        Users users = usersRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUsername(users)
                .stream().map(commentMapper::mapToDto)
                .collect(toList());
    }
}
