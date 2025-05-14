package com.example.ecommercebackend.anotation;

import com.example.ecommercebackend.exception.BadRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Aspect
@Component
public class NotNullValidationField {
    @Before("execution(* com.example.ecommercebackend.controller..*(..))") // tüm methodları kapsar
    public void validateNotNullFields(JoinPoint joinPoint) throws IllegalAccessException {
        System.out.println("not null field methoduna girdi");
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) continue;

            Field[] fields = arg.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(NotNullField.class)) {
                    field.setAccessible(true);
                    Object value = field.get(arg);
                    if (value == null) {
                        throw new BadRequestException(field.getName() + " must not be null.");
                    }
                }
            }
        }
    }
}
