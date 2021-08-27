package com.question.modules.exam.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * 多选题
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="更新多选题请求对象", description="更新多选题请求对象")
public class UpdateExamMultiReq {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "题目id不能为空")
    @ApiModelProperty(value = "题目id")
    private Integer id;

    @NotBlank(message = "多选题问题内容不能为空")
    @ApiModelProperty(value = "多选题问题内容")
    private String question;

    @NotNull(message = "多选题选项不能为空")
    @ApiModelProperty(value = "多选题选项")
    private List<String> choices;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @NotBlank(message = "多选题问题内容不能为空")
    @ApiModelProperty(value = "是否为必填 1代表必填")
    private Integer required;

    @NotNull(message = "多选题答案不能为空")
    @ApiModelProperty(value = "多选题答案")
    private List<String> answer;

    @ApiModelProperty(value = "多选题分值")
    private Integer score;
}
