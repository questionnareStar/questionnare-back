package com.question.modules.exam.entities.vo.statistics;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 考试问卷多选题数据
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="考试问卷多选题数据", description="考试问卷多选题数据")
public class ExamMultiStatistics {

    @ApiModelProperty(value = "问题类型 1：填空题 2：多选题 3：单选题")
    Integer type;

    @ApiModelProperty(value = "问题题目")
    String question;

    @ApiModelProperty(value = "问题答案")
    List<String> answers;

    @ApiModelProperty(value = "用户回答")
    List<List<String>> userAnswers;

    @ApiModelProperty(value = "回答数")
    Integer answerNumbers;

    @ApiModelProperty(value = "回答正确数")
    Integer correctNumbers;
}
