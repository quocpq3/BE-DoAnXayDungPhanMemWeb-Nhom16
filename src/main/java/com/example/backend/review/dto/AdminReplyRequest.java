package com.example.backend.review.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminReplyRequest {
    @NotBlank(message = "Nội dung trả lời không được để trống")
    private String adminReply;
}