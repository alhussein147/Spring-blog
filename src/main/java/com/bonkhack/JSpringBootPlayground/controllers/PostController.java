package com.bonkhack.JSpringBootPlayground.controllers;

import com.bonkhack.JSpringBootPlayground.dto.PostResponse;
import com.bonkhack.JSpringBootPlayground.models.Post;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.services.PostsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostsService postsService;

    public PostController(PostsService postService) {
        this.postsService = postService;
    }

    @PostMapping
    public PostResponse createPost(
                                   @RequestParam String title,
                                   @RequestParam String content,
                                   @AuthenticationPrincipal User currentUser
    ) {
        Post post = postsService.createPost(currentUser.getId(), title, content);
        return postsService.toPostResponse(post);
    }

    @GetMapping("/me")
    public List<PostResponse> myPosts(@AuthenticationPrincipal User currentUser) {
        List<Post> posts = postsService.getPostsByUserId(currentUser.getId());
        return posts.stream().map(postsService::toPostResponse).toList();
    }

    @GetMapping("/user/{userId}")
    public List<PostResponse> getPostsByUser(@PathVariable Long userId) {
        List<Post> posts = postsService.getPostsByUserId(userId);
        return posts.stream().map(postsService::toPostResponse).toList();
    }


    @PutMapping("/{postId}")
    public PostResponse updatePost(@PathVariable Long postId,
                                   @AuthenticationPrincipal User currentUser,
                                   @RequestParam String title,
                                   @RequestParam String content) {

        Post post = postsService.updatePost(postId, title, content, currentUser.getId());
        return postsService.toPostResponse(post);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal User currentUser) {
        postsService.deletePost(postId, currentUser.getId());
    }
}
