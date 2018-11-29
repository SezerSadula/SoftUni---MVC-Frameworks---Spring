package com.softuni.residentevil.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {

    private Set<String> fieldsToValidate;

    public void initialize(EqualFields constraint) {
        this.fieldsToValidate = Arrays.stream(constraint.fields()).collect(Collectors.toSet());
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        int fieldsUsed = 0;
        final Set<Object> values = new HashSet<>();
        final Field[] declaredFields = obj.getClass().getDeclaredFields();

        for (final Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (this.fieldsToValidate.contains(declaredField.getName())) {
                try {
                    values.add(declaredField.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                fieldsUsed++;
            }
        }

        return values.size() == 1 && this.fieldsToValidate.size() == fieldsUsed;
    }
}
