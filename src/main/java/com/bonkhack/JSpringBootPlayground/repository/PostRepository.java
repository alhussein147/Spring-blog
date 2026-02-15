package com.bonkhack.JSpringBootPlayground.repository;

import com.bonkhack.JSpringBootPlayground.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository  extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);
}
