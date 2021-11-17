package com.mydiaryapplication.userdiary.validation;

import com.mydiaryapplication.userdiary.userdata.UserData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<PasswordMatch, UserData> {

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

    }

    @Override
    public boolean isValid(UserData userData, ConstraintValidatorContext constraintValidatorContext) {
            return userData.getPassword().equals(userData.getConfirmPassword());
    }
}
