package com.question.modules.exam.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "考试问卷回答用户对象", description = "考试问卷回答用户对象")
public class FeedBackVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷id")
    private Integer questionnaireId;

    @ApiModelProperty(value = "用户提交记录")
    private List<ExamAnswerRecordVo> answerRecords;

}
