package ru.rosbank.javaschool.validator;

import ru.rosbank.javaschool.constraint.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {


    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }


    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return contactField != null && contactField.matches("[0-9]+")
                && (contactField.length() > 8) && (contactField.length() < 14);
    }
}
