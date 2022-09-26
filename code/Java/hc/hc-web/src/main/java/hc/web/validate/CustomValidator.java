package hc.web.validate;

import hc.web.bean.ValidateUser;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class CustomValidator implements ConstraintValidator<CustomValidate, ValidateUser> {

    @Override
    public void initialize(CustomValidate constraintAnnotation) {
        log.info("初始化自定义Validator");
    }

    @Override
    public boolean isValid(ValidateUser validateUser, ConstraintValidatorContext constraintValidatorContext) {
        return "hc".equals(validateUser.getUsername());
    }

}
