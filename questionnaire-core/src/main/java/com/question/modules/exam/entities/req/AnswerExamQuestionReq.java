package com.question.modules.exam.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 问卷星球开发团队
 * @version 1.0
 * @date 2021/8/22 10:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写考试问卷对象", description = "填写问卷视图层")
public class AnswerExamQuestionReq {

    @NotNull(message = "题目类型不可为空")
    @ApiModelProperty(value = "题目类型 1 填空题 2多选题 3单选题")
    private Integer itemType;

    @NotNull(message = "题目id不可为空")
    @ApiModelProperty(value = "题目id")
    private Integer topicId;

    @ApiModelProperty(value = "答案,多选题直接传递数组，填空题/单选题/评分题也传递数据，系统读取第一个值")
    private List<String> answerList;
}
