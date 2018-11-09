package com.fusi24.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfTokenRepository;

import com.fusi24.auth.service.SecretService;

@Configuration
public class CSRFConfig {

    @Autowired
    SecretService secretService;

    @Bean
    @ConditionalOnMissingBean
    public CsrfTokenRepository jwtCsrfTokenRepository() {
        return new JWTCsrfTokenRepository(secretService.getHS256SecretBytes());
    }
}
