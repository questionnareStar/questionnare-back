package com.question.modules.question.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 题目回答情况
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "QuestionAnswer对象", description = "用户问题-回答")
public class QuestionAnswer {
    @ApiModelProperty(value = "问题题目")
    String question;
    @ApiModelProperty(value = "用户答案")
    List<String> userAnswer;
    @ApiModelProperty(value = "选项")
    List<String> choices;
    @ApiModelProperty(value = "用户给分")
    Integer score;
    @ApiModelProperty(value = "总分")
    Integer maxScore;
    @ApiModelProperty(value = "问题类型 1：填空题 2：评分题 3：多选题 4：单选题")
    Integer type;
}
