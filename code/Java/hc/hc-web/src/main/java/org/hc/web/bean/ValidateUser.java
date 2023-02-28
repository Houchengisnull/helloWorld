package org.hc.web.bean;

import org.hc.web.validate.CustomValidate;
import lombok.Data;
import javax.validation.constraints.NotNull;

@CustomValidate
@Data
public class ValidateUser {

    @NotNull(message = "用户名不可为空")
    private String username;

}