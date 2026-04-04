package com.example.backend.configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tên_của_thịnh",
                "api_key", "số_key_của_thịnh",
                "api_secret", "mã_bí_mật_của_thịnh"
        ));
    }
}