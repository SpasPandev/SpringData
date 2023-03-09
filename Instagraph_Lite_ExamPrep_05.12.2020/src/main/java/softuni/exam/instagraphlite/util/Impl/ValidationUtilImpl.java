package softuni.exam.instagraphlite.util.Impl;

import org.springframework.stereotype.Component;
import softuni.exam.instagraphlite.util.ValidationUtil;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @Override
    public <E> boolean isValid(E entity) {

        return validator.validate(entity).isEmpty();
    }
}