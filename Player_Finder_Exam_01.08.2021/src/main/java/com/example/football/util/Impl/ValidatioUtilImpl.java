package com.example.football.util.Impl;

import com.example.football.util.ValidatioUtil;
import org.springframework.stereotype.Component;

import javax.validation.Validator;

@Component
public class ValidatioUtilImpl implements ValidatioUtil {

    private final Validator validator;

    public ValidatioUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <T> boolean isValid(T entity) {

        return validator.validate(entity).isEmpty();
    }
}
