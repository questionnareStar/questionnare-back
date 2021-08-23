package com.question.modules.question.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 填空题
 *
 * @author 问卷星球团队
 * @since 2021-08-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="创建填空题请求对象", description="创建填空题请求对象")
public class CreateFillBlankReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "填空题内容不能为空")
    @ApiModelProperty(value = "填空题内容")
    private String question;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "是否为必填 true表示必填")
    private boolean required;

}
