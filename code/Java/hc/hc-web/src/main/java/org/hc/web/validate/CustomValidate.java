package org.hc.web.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD, PARAMETER, TYPE})
@Constraint(validatedBy = CustomValidator.class)
public @interface CustomValidate {

    String message() default "当前用户不是hc";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
