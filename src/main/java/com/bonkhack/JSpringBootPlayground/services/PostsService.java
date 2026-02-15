package com.bonkhack.JSpringBootPlayground.services;

import com.bonkhack.JSpringBootPlayground.dto.PostResponse;
import com.bonkhack.JSpringBootPlayground.exceptions.BadRequestException;
import com.bonkhack.JSpringBootPlayground.exceptions.ResourceNotFoundException;
import com.bonkhack.JSpringBootPlayground.models.Post;
import com.bonkhack.JSpringBootPlayground.models.User;
import com.bonkhack.JSpringBootPlayground.repository.PostRepository;
import com.bonkhack.JSpringBootPlayground.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostsService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public Post createPost(Long userId, String title, String content) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user was not found"));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setUser(user);
        return postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    public List<Post> getPostsByUserId(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with the entered id wad not found"));
        return postRepository.findByUserId(userId);
    }


    @Transactional
    public Post updatePost(Long postId, String title, String content, Long userId) {
        Post post = getPostById(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new BadRequestException("You are not allowed to edit this post");
        }

        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = getPostById(postId);

        if (!post.getUser().getId().equals(userId)) {
            throw new BadRequestException("You are not allowed to delete this post");
        }

        postRepository.delete(post);
    }

    public PostResponse toPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt().toString(),
                post.getUser().getId()
        );
    }

}
