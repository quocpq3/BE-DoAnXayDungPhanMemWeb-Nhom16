package com.example.backend.common;

public class DataUtils {
    public static String toSlug(String input) {
        if (input == null) return "";
        // Chuyển sang chữ thường, xóa dấu Tiếng Việt, thay khoảng trắng bằng gạch ngang
        String normalized = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        return normalized.toLowerCase()
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("^-+|-+$", "");
    }
}