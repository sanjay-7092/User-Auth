package com.nasa.auth.filter;

import com.nasa.auth.dto.User;
import com.nasa.auth.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException,ServletException{

        String token = request.getHeader("Authorization");
        if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            boolean validUser = JwtUtil.validateToken(token);
            if(validUser){
                User user = JwtUtil.extractUser(token);
                Collection<GrantedAuthority> authorities =getAuthorities(token);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null,authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    private Collection<GrantedAuthority> getAuthorities(String token){
        Collection<GrantedAuthority> authorities= new HashSet<>();
        Set<String> roles = JwtUtil.splitRoles(token);
        for(String role:roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role)));
        }
        return authorities;
    }

}
