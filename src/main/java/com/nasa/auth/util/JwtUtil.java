package com.nasa.auth.util;

import javax.crypto.SecretKey;

import com.nasa.auth.dto.User;
import com.nasa.auth.entity.RoleEntity;
import com.nasa.auth.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUtil {


    private static final String SECRET = "Nasa-super-secret-key-used-for-authentication";
    private static final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));


    public static String generateToken(UserEntity userEntity){
        return Jwts.builder()
                .setSubject(userEntity.getEmail())
                .claim("roles",mergeRoles(userEntity.getRoles()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*800))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }
    public static boolean validateToken(String token){
        Claims claims = extractClaims(token);
        return claims.getExpiration().after(new Date());

    }

    private static Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public static User extractUser(String token){
        User user =new User();
        Claims claims= extractClaims(token);
        user.setEmail(claims.getSubject());
        return user;
    }
    private static String mergeRoles(Set<RoleEntity> roles){
        return roles.stream().map(RoleEntity::getName).collect(Collectors.joining(","));
    }

    public static Set<String> splitRoles(String token){
        Claims claims= extractClaims(token);
        String roles = claims.get("roles", String.class);
        if(roles==null){
            roles="";  // need to remove
        }
        return Arrays.stream(roles.split(",")).collect(Collectors.toSet());
    }

}
