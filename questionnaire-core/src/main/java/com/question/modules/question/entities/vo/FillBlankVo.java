package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/22 10:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="填空题问卷视图层", description="填空题问卷视图层")
public class FillBlankVo {

    @ApiModelProperty(value = "题目类型")
    private Integer itemType;

    @ApiModelProperty(value = "题目id")
    private Integer topicId;

    @ApiModelProperty(value = "问题内容")
    private String question;

    @ApiModelProperty(value = "是否为必填 true表示必填")
    private boolean required;

    @ApiModelProperty(value = "题目顺序")
    private Integer sequence;



}
