package com.question.modules.question.entities.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/22 10:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "填写问卷对象", description = "填空题问卷视图层")
public class FillInQuestionnaireReq {

    @NotNull(message = "题目类型不可为空")
    @ApiModelProperty(value = "题目类型  ")
    private Integer itemType;

    @NotNull(message = "题目id不可为空")
    @ApiModelProperty(value = "题目id")
    private Integer topicId;

    @ApiModelProperty(value = "答案,多选题直接传递数组，填空题/单选题/评分题也传递数据，系统读取第一个值")
    private List<String> answerList;
}
