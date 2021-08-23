package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="评分题问卷视图层", description="评分题问卷视图层")
public class MarkQuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "题目类型")
    private Integer itemType;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "问题")
    private String question;

    @ApiModelProperty(value = "总分")
    private Integer maxScore;

    @ApiModelProperty(value = "是否为必填")
    private Integer required;

    @ApiModelProperty(value = "题目顺序")
    private Integer sequence;

}
