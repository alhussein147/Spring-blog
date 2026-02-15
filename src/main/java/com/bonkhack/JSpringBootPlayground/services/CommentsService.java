package com.bonkhack.JSpringBootPlayground.services;

import com.bonkhack.JSpringBootPlayground.exceptions.BadRequestException;
import com.bonkhack.JSpringBootPlayground.exceptions.ResourceNotFoundException;
import com.bonkhack.JSpringBootPlayground.models.Comment;
import com.bonkhack.JSpringBootPlayground.models.Post;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.repository.CommentRepository;
import com.bonkhack.JSpringBootPlayground.repository.PostRepository;
import com.bonkhack.JSpringBootPlayground.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentsService(CommentRepository commentRepository,
                           PostRepository postRepository,
                           UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public Comment addComment(Long postId, Long userId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setText(text);

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getCommentsByUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return commentRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        if (!comment.getUser().getId().equals(userId)) {
            throw new BadRequestException("You cannot delete this comment");
        }

        commentRepository.delete(comment);
    }


}
