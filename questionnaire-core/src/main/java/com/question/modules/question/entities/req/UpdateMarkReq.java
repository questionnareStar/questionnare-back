package com.question.modules.question.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "更新评分题对象", description = "更新评分题对象")
public class UpdateMarkReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "题目id不能为空")
    @ApiModelProperty(value = "题目id")
    private Integer id;

    @NotBlank(message = "评分题内容不能为空")
    @ApiModelProperty(value = "评分题内容")
    private String question;

    @NotNull(message = "评分题总分不能为空")
    @ApiModelProperty(value = "总分")
    private Integer maxScore;

    @ApiModelProperty(value = "描述信息")
    private String desc;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;

}
