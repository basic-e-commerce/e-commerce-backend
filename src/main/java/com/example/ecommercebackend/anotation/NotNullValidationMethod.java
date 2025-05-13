package com.example.ecommercebackend.anotation;

import com.example.ecommercebackend.exception.BadRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class NotNullValidationMethod {

    @Before("@annotation(com.example.ecommercebackend.anotation.NotNullMethod)")
    public void validateNotNullParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();

        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                String paramName = parameters[i].getName();
                throw new BadRequestException(paramName + " must not be null.");
            }
        }
    }


}
