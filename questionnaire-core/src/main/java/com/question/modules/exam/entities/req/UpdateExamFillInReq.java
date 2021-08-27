package com.question.modules.exam.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 填空题
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "更新填空题请求对象", description = "更新填空题请求对象")
public class UpdateExamFillInReq {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "题目id不能为空")
    @ApiModelProperty(value = "题目id")
    private Integer id;

    @NotBlank(message = "填空题内容不能为空")
    @ApiModelProperty(value = "填空题内容")
    private String question;

    @NotBlank(message = "标准答案不能为空")
    @ApiModelProperty(value = "填空题答案")
    private String answer;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @NotBlank(message = "填空题问题内容不能为空")
    @ApiModelProperty(value = "是否为必填 true表示必填")
    private Integer required;

}
