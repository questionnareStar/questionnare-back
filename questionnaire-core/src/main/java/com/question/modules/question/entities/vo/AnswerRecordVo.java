package com.question.modules.question.entities.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author cv大魔王
 * @version 1.0
 * @date 2021/8/22 10:53
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="用户回答视图层", description="用户回答视图层")
public class AnswerRecordVo {
    @ApiModelProperty(value = "回答时间")
    private Date answerTime;
    @ApiModelProperty(value = "记录")
    private Integer recordId;
    @ApiModelProperty(value = "用户名")
    private String userAccount;
}
