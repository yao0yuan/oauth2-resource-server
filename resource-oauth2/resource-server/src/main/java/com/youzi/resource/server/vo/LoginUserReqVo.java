package com.youzi.resource.server.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
public class LoginUserReqVo implements Serializable {

    @NotEmpty(message = "用户名不能为空")
    private String loginName;

    @NotEmpty(message = "密码不能为空")
    private String password;


}
