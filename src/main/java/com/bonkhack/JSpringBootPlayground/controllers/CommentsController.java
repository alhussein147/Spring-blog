package com.bonkhack.JSpringBootPlayground.controllers;

import com.bonkhack.JSpringBootPlayground.models.Comment;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.services.CommentsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/comments")
public class CommentsController {

    private final CommentsService commentService;

    public CommentsController(CommentsService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public Comment addComment(@RequestParam Long postId,
                              @AuthenticationPrincipal User currentUser,
                              @RequestParam String text) {
        return commentService.addComment(postId, currentUser.getId(), text);
    }

    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }

    @GetMapping("/me")
    public List<Comment> myComments(@AuthenticationPrincipal User currentUser) {
        return commentService.getCommentsByUser(currentUser.getId());
    }


    @GetMapping("/user/{userId}")
    public List<Comment> getCommentsByUser(@PathVariable Long userId) {
        return commentService.getCommentsByUser(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id,
                       @AuthenticationPrincipal User currentUser) {
        commentService.deleteComment(id, currentUser.getId());
    }


}
