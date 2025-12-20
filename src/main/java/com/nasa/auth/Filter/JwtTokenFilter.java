package com.nasa.auth.Filter;

import com.nasa.auth.Entity.UserEntity;
import com.nasa.auth.Util.JWTUtil;
import com.nasa.auth.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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
            boolean validUser = JWTUtil.validateToken(token,userService);
            String userName = JWTUtil.extractUserName(token);
            UserEntity userEntity = userService.findByUserName(userName);
            if(validUser){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity,null,null);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
