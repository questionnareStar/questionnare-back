package com.question.modules.exam.entities;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 题目回答情况
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("exam_multi_answer")
@ApiModel(value="ExamMultiAnswer对象", description="考试多选题对象")
public class ExamQuestionAnswer {
    @ApiModelProperty(value = "问题题目")
    String question;
    @ApiModelProperty(value = "用户答案")
    String userAnswer;
    @ApiModelProperty(value = "标准答案")
    String referenceAnswer;
    @ApiModelProperty(value = "得分")
    Integer score;
    @ApiModelProperty(value = "问题类型 1：填空题 2：多选题 3：单选题")
    Integer type;
}
