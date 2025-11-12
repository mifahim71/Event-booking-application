package com.eventbooking.auth_service.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtUtil {

    private static final String SECRETKEY = "f9a2b84d7e1c4a98b7fbc69a8d3f2e1c9d6a4b5e8f0a7c3d9b1e6f2c7a8d4b073site4we";

    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRETKEY));
    }

    public String generateToken(Long userId, String email, String roles){
        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("role", roles)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .signWith(secretKey())
                .compact();
    }


    public void validateToken(String token){
        try{
            Jwts.parser().verifyWith(secretKey()).build().parseSignedClaims(token);
        } catch (SignatureException e){
            throw new JwtException("Invalid Jwt Signature");
        } catch (JwtException e){
            throw new JwtException("Invalid jwt");
        }
    }

    public String getUserId(String token){
        return extractAllClaims(token).getSubject();
    }

    public String getEmail(String token){
        return extractAllClaims(token).get("email", String.class);
    }

    public String getRoles(String token){
        return extractAllClaims(token).get("role", String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
