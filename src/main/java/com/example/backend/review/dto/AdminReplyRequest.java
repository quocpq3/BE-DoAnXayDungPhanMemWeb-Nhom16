package com.example.backend.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminReplyRequest {
    @NotBlank(message = "ADMIN_REPLY_REQUIRED")
    private String adminReply;
}