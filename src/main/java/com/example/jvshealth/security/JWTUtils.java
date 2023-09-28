package com.example.jvshealth.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class JWTUtils {
    Logger logger = Logger.getLogger(JWTUtils.class.getName());
    @Value("${jwt-secret}")
    private String jwtSecret;

    @Value("${jwt-expiration-ms}")
    private int jwtExpirationMs;



    public String generateJwtToken(MyDoctorDetails myDoctorDetails) {
        return Jwts.builder()
                .setSubject((myDoctorDetails.getUsername())) // just the user email
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    // For every single request
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    // For every single request

    public boolean validateJwtToken(String authToken) {
        try {
            // Try to parse and verify the token
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            // If parsing and verification are successful, return true to indicate a valid token
            return true;
        } catch (SecurityException e) {
            // Handle an invalid JWT signature
            logger.log(Level.SEVERE, "Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            // Handle an invalid JWT token
            logger.log(Level.SEVERE, "Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            // Handle an expired JWT token
            logger.log(Level.SEVERE, "JWT token is expired: {}", e.getMessage());
            // Token is expired, return false to indicate rejection
            return false;
        } catch (UnsupportedJwtException e) {
            // Handle an unsupported JWT token
            logger.log(Level.SEVERE, "JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            // Handle an empty JWT claims string
            logger.log(Level.SEVERE, "JWT claims string is empty: {}", e.getMessage());
        }
        // If any exception is caught, return false to indicate a validation failure
        return false;
    }


}
