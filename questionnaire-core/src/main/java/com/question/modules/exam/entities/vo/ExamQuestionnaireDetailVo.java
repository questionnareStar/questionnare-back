package com.question.modules.exam.entities.vo;

import com.question.modules.question.entities.Questionnaire;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 考试问卷详细
 * </p>
 *
 * @author 问卷星球团队
 * @since 2021-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考试问卷详情视图层对象", description = "考试问卷详情视图层对象")
public class ExamQuestionnaireDetailVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷信息")
    private Questionnaire questionnaire;

    @ApiModelProperty(value = "题目集合")
    private List<ExamQuestionVo> questionList;


}
