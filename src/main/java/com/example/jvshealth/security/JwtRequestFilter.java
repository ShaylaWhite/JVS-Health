package com.example.jvshealth.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.logging.Logger;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    Logger logger = Logger.getLogger(JwtRequestFilter.class.getName());
}
