package com.example.jvshealth.security;

import net.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {
    /**
     Defines a bean for the BCryptPasswordEncoder, which is used for hashing passwords.
     *
     @return BCryptPasswordEncoder bean
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     Defines a bean for the JwtRequestFilter, responsible for handling JWT-based authentication.
     *
     @return JwtRequestFilter bean
     */
    @Bean
    public JwtRequestFilter authJwtRequestFilter() {
        return new JwtRequestFilter();
    }
}
