package com.question.modules.exam.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考试问卷题目图层对象", description = "考试问卷题目视图层对象")
public class ExamQuestionVo {

    @ApiModelProperty(value = "问题id")
    Integer id;

    @ApiModelProperty(value = "问题类型 1填空 2多选 3单选")
    Integer type;

    @ApiModelProperty(value = "问题题目")
    String question;

    @ApiModelProperty(value = "问题选项")
    List<String> choices;

    @ApiModelProperty(value = "问题参考答案(若为选择题)")
    List<String> answers;

    @ApiModelProperty(value = "问题参考答案(若为填空题)")
    String answer;

    @ApiModelProperty(value = "是否必答")
    Integer required;

    @ApiModelProperty(value = "问题描述")
    String desc;

}
