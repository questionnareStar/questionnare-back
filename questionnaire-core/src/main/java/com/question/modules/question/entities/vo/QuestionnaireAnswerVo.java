package com.question.modules.question.entities.vo;

import com.question.modules.question.entities.QuestionAnswer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考试问卷问卷详细回答结果视图层对象", description = "考试问卷问卷详细回答结果视图层对象")
public class QuestionnaireAnswerVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户账号")
    private String account;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaireId;

    @ApiModelProperty(value = "每道题的回答情况")
    private List<QuestionAnswer> questionAnswers;


}
