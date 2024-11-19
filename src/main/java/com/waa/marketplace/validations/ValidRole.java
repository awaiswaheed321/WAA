package com.waa.marketplace.validations;

import com.waa.marketplace.enums.Role;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleValidator.class)
public @interface ValidRole {

    String message() default "Invalid role. Allowed values are {values}.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Role[] values(); // Allowed roles
}

