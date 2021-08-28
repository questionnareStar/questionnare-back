package com.question.modules.question.entities.vo;

import com.question.modules.question.entities.Questionnaire;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "问卷回答人数趋势视图层对象", description = "问卷回答人数趋势视图层对象")
public class AnswerNumberVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问卷信息")
    Questionnaire questionnaire;

    @ApiModelProperty(value = "每日回答数量")
    List<AnswerNumber> answerNumbers;
    @ApiModelProperty(value = "原始数据")
    List<AnswerRecordVo> rawRecords;

    public List<AnswerNumber> createAnswerNumbers() {
        this.answerNumbers = new ArrayList<>();
        return this.answerNumbers;
    }

    public List<AnswerRecordVo> createAnswerRecords() {
        this.rawRecords = new ArrayList<>();
        return this.rawRecords;
    }
}
