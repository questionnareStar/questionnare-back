package com.question.modules.exam.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 题目回答情况
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ExamQuestionAnswer对象", description="用户问题-回答")
public class ExamQuestionAnswer {
    @ApiModelProperty(value = "问题题目")
    String question;
    @ApiModelProperty(value = "用户答案-单选题、填空题")
    String userAnswer;
    @ApiModelProperty(value = "选项-选择题")
    List<String> choices;
    @ApiModelProperty(value = "用户答案-多选题")
    List<String> userAnswers;
    @ApiModelProperty(value = "标准答案-单选题、填空题")
    String referenceAnswer;
    @ApiModelProperty(value = "标准答案-多选题")
    List<String> referenceAnswers;
    @ApiModelProperty(value = "得分")
    Integer score;
    @ApiModelProperty(value = "问题类型 1：填空题 2：多选题 3：单选题")
    Integer type;
}
