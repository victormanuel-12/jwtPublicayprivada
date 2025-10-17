package com.spring.llavepublicpriv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.spring.llavepublicpriv.services.models.validation.UserValidation;

@Configuration
public class ValidationConfig {

    @Bean
    public UserValidation userValidation(){
        return new UserValidation();
    }
}
