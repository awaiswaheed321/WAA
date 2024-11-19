package com.waa.marketplace.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RoleValidator implements ConstraintValidator<ValidRole, String> {

    private List<String> allowedRoles;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        allowedRoles = Arrays.stream(constraintAnnotation.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && allowedRoles.contains(value.toUpperCase());
    }
}
