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
public class NotNullValidationParam {

    @Before("execution(* *(.., @com.example.ecommercebackend.anotation.NotNullParam (*), ..))")
    public void validateNotNullParams(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(NotNullParam.class)) {
                if (args[i] == null) {
                    String paramName = parameters[i].getName(); // dikkat: derleme ayarına bağlı
                    throw new BadRequestException("Parameter '" + paramName + "' must not be null.");
                }
            }
        }
    }

}
