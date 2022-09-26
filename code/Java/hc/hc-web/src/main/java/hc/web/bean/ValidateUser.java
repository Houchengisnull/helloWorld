package hc.web.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidateUser {

    @NotNull(message = "用户名不可为空")
    private String username;

}