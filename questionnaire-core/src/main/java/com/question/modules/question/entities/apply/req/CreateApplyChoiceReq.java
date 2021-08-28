package com.question.modules.question.entities.apply.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 多选题
 *
 * @author 问卷星球团队
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "创建报名问卷选择题对象", description = "创建报名问卷选择题对象")
public class CreateApplyChoiceReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "选择题内容不能为空")
    @ApiModelProperty(value = "选择题内容")
    private String question;

    @ApiModelProperty(value = "选择题选项")
    private List<ApplyOptionsReq> choices;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;

}
