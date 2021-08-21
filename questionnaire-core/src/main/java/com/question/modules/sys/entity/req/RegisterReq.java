package com.question.modules.sys.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/20 22:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户注册请求对象", description="用户注册请求对象")
public class RegisterReq implements Serializable {

    @NotBlank(message = "账号(邮箱)不能为空")
    @ApiModelProperty(value = "账号(邮箱)")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码不能为空")
    private String code;

}
