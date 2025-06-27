package com.duyhiep523.user_management.annotation;

import com.duyhiep523.user_management.common.ResponseMessage;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
    Class<? extends Enum<?>> enumClass();

    String message() default ResponseMessage.Common.INVALID_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}