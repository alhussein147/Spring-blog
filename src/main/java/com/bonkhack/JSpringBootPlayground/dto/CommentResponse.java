package com.bonkhack.JSpringBootPlayground.dto;

public record CommentResponse(Long id, String text, String createdAt, Long postId, Long userId) {
}
