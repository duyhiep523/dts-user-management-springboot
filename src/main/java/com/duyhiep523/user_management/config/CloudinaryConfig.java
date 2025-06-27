package com.duyhiep523.user_management.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {


    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "divwaxpyg",
                "api_key", "826643343675892",
                "api_secret", "EclroY97EUK4mxH8lNh00PbAABA"
        ));
    }
}