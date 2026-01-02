package com.nasa.auth.aspect;

import com.nasa.auth.annotation.RateLimit;
import com.nasa.auth.dto.User;
import com.nasa.auth.service.RateLimitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@Aspect
@AllArgsConstructor
@Slf4j
public class AuthAspect {

    private final RateLimitService rateLimitService;

    @Around("@annotation(rateLimit)")
    public Object applyRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable{
        log.info("Checking the rate limit for a method ".concat(joinPoint.getSignature().getName()));
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();
        String userId = user.getUserId();


        String key = "rate_limit:".concat(userId).concat(":")
                                    .concat(joinPoint.getSignature()
                                            .toShortString());

        boolean isAllowed = rateLimitService.isAllowed(key, rateLimit.limit(), rateLimit.windowSeconds());

        if(!isAllowed){
            log.error("Too many request in method:{}",joinPoint.getSignature().getName());
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Rate Limit Exceeded. Try Again Later"
            );
        }

        return joinPoint.proceed();

    }
}
