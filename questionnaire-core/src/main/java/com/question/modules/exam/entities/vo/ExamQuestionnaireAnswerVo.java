package com.question.modules.exam.entities.vo;

import com.question.modules.exam.entities.ExamQuestionAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考试问卷问卷详细回答结果视图层对象", description = "考试问卷问卷详细回答结果视图层对象")
public class ExamQuestionnaireAnswerVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaireId;

    @ApiModelProperty(value = "总客观题得分")
    private Integer totalScore;

    @ApiModelProperty(value = "每道题的回答情况")
    private List<ExamQuestionAnswer> questionAnswers;


}
