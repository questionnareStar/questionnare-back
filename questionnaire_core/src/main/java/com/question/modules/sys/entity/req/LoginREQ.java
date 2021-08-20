package com.question.modules.sys.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/20 22:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="登录信息", description="登录信息")
public class LoginREQ implements Serializable {

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

}
