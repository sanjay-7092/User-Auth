package com.nasa.auth.util;

import javax.crypto.SecretKey;

import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JWTUtil {

    private static final String SECRET = "Nasa-super-secret-key-used-for-authentication";
    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    public static String generateToken(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*80))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }

    public static boolean validateToken(String token, UserService userService){
        Claims claims = extractClaims(token);
        UserEntity userEntity = userService.findByUserName(claims.getSubject());
        if(userEntity!=null){
            return claims.getExpiration().after(new Date());
        }
        return false;
    }

    private static Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public static String extractUserName(String token){
        return extractClaims(token).getSubject();
    }

}
