package com.question.modules.exam.entities.vo.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 考试问卷数据统计
 *
 * @author 问卷星球团队
 * @since 2021-08-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="考试问卷数据统计视图层对象", description="考试问卷数据统计视图层对象")
public class ExamStatisticsVo {

    @ApiModelProperty(value = "标题")
    String Head;

    @ApiModelProperty(value = "简介")
    String introduction;

    @ApiModelProperty(value = "开始时间")
    Date startTime;

    @ApiModelProperty(value = "结束时间")
    Date endTime;

    @ApiModelProperty(value = "是否发布")
    Integer isReleased;

    @ApiModelProperty(value = "填写人数")
    Integer writeNum;

    @ApiModelProperty(value = "问卷密钥")
    String code;

    @ApiModelProperty(value = "是否展示题号 true展示 false不展示")
    Boolean isSerial;

    @ApiModelProperty(value = "题目数据")
    List<Object> questionStatics;
}
