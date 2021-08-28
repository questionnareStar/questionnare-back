package com.question.modules.exam.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考试问卷多选题视图层对象", description = "考试问卷多选题视图层对象")
public class ExamMultiVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "问题内容")
    private String question;

    @ApiModelProperty(value = "选项")
    private String choices;

    @ApiModelProperty(value = "标准答案")
    private List<String> answer;

    @ApiModelProperty(value = "是否必答")
    private Integer required;

    @ApiModelProperty(value = "题目备注")
    private String desc;

    @ApiModelProperty(value = "分值")
    private Integer score;


}
