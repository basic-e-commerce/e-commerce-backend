package com.example.ecommercebackend.anotation;

import com.example.ecommercebackend.exception.BadRequestException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

@Aspect
@Component
public class PhoneValidationField {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+90\\d{10}$");

    @Before("execution(* com.example.ecommercebackend.controller..*(..))")
    public void validateNotNullFields(JoinPoint joinPoint) throws IllegalAccessException {
        for (Object arg : joinPoint.getArgs()) {
            if (arg == null) continue;

            Field[] fields = arg.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(arg);

                // Telefon numarası kontrolü: null değilse kontrol et
                if (field.isAnnotationPresent(ValidPhoneNumber.class) && value != null) {
                    String phone = value.toString();
                    if (!PHONE_PATTERN.matcher(phone).matches()) {
                        throw new BadRequestException(
                                field.getName() + " must be a valid Turkish phone number in the format +905XXXXXXXXX"
                        );
                    }
                }
            }
        }
    }
}
