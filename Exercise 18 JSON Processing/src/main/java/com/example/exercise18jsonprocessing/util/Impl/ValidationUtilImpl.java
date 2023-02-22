package com.example.exercise18jsonprocessing.util.Impl;

import com.example.exercise18jsonprocessing.util.ValidationUtil;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E entity) {

        return validator.validate(entity).isEmpty();
    }
}
