package com.question.modules.exam.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 单选题
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "创建单选题请求对象", description = "创建单选题请求对象")
public class CreateExamSingleReq {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "单选题问题内容不能为空")
    @ApiModelProperty(value = "单选题问题内容")
    private String question;

    @NotNull(message = "单选题选项不能为空")
    @ApiModelProperty(value = "单选题选项")
    private List<String> choices;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @NotBlank(message = "单选题问题内容不能为空")
    @ApiModelProperty(value = "是否为必填 1代表必填")
    private Integer required;

    @NotNull(message = "单选题答案不能为空")
    @ApiModelProperty(value = "单选题答案")
    private String answer;

    @ApiModelProperty(value = "单选题分值")
    private Integer score;

}
