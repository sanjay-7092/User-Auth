package com.nasa.auth.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AuthAspect {

    @Before("within(com.nasa.auth.service..*)")
    public void handleLogging(){
        log.info("Enteirng into Service");
    }
}
