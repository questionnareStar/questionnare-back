package com.question.modules.exam.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 填空题
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "创建填空题请求对象", description = "创建填空题请求对象")
public class CreateExamFillInReq {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "填空题问题内容不能为空")
    @ApiModelProperty(value = "多选题问题内容")
    private String question;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @NotBlank(message = "填空题问题是否必填不能为空")
    @ApiModelProperty(value = "是否为必填 1代表必填")
    private Integer required;

    @NotBlank(message = "标准答案不能为空")
    @ApiModelProperty(value = "填空题答案")
    private String answer;

}
