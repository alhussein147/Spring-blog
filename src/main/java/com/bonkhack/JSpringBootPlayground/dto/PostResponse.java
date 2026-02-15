package com.bonkhack.JSpringBootPlayground.dto;

public record PostResponse(Long id, String title, String content, String createdAt, Long userId) {
}
