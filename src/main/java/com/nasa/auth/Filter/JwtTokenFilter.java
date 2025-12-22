package com.nasa.auth.Filter;

import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.util.JwtUtil;
import com.nasa.auth.service.UserService;
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
import java.util.Arrays;
import java.util.Collection;
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
    protected JwtTokenFilter(UserService userService){
        this.userService=userService;
    }
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws IOException,ServletException{

        String token = request.getHeader("Authorization");
        if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            boolean validUser = JwtUtil.validateToken(token,userService);
            if(validUser){
                String userName = JwtUtil.extractUserName(token);
                UserEntity userEntity = userService.findByUserName(userName);
                Collection<GrantedAuthority> authorities =getAuthorities(userEntity);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity,null,authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    private Collection<GrantedAuthority> getAuthorities(UserEntity userEntity){
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_".concat(userEntity.getRole()));
        return Arrays.asList(grantedAuthority);
    }
}
