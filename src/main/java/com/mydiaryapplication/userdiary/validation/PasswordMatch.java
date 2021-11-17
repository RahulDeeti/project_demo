package com.mydiaryapplication.userdiary.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidation.class)
public @interface PasswordMatch {
    String message() default "Passwords must match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
